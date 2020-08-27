package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.Window
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.LOGO_PLACEHOLDER
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.PLACEHOLDER_END
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.PLACEHOLDER_START
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import kotlin.math.roundToInt

class SezzleUtils {

    companion object {

        fun <T> requireNonNull(obj: T?, message: String?) {
            if (obj == null) {
                throw NullPointerException(message)
            }
        }

        fun hideActionBar(activity: AppCompatActivity) {
            activity.window.requestFeature(Window.FEATURE_ACTION_BAR)
            if (activity.actionBar != null) {
                activity.actionBar!!.hide()
            } else if (activity.supportActionBar != null) {
                activity.supportActionBar?.hide()
            }
        }

        fun debuggableWebView(context: Context) {
            if (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        fun createSpannableForText(
            template: String,
            textSize: Float,
            sezzleLogoType: SezzleLogoType,
            context: Context
        ): SpannableString? {
            val resources = context.resources
            val logoDrawable = resources.getDrawable(
                sezzleLogoType.getDrawableRes()
            ).mutate()
            return this.getSpannable(
                template,
                textSize,
                logoDrawable,
                sezzleLogoType,
                resources
            )
        }

        private fun getSpannable(
            template: String,
            textSize: Float,
            logoDrawable: Drawable?,
            sezzleLogoType: SezzleLogoType,
            resources: Resources
        ): SpannableString? {
            val spannableString: SpannableString
            val index: Int = template.indexOf(LOGO_PLACEHOLDER)
            if (logoDrawable != null && index != -1) {
                spannableString = SpannableString(template)
                val imageSpan: ImageSpan? = this.getLogoSpan(
                    textSize,
                    logoDrawable,
                    sezzleLogoType,
                    resources
                )
                spannableString.setSpan(
                    imageSpan,
                    index,
                    index + LOGO_PLACEHOLDER.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            } else {
                val onlyText: String = template.replace(LOGO_PLACEHOLDER, "")
                spannableString = SpannableString(onlyText)
            }
            return spannableString
        }

        private fun getLogoSpan(
            textSize: Float,
            logoDrawable: Drawable,
            sezzleLogoType: SezzleLogoType,
            resources: Resources
        ): ImageSpan? {
            val logoHeight = textSize * 1f
            val ratio =
                logoDrawable.intrinsicWidth.toFloat() / logoDrawable.intrinsicHeight

            logoDrawable.setBounds(
                0, 0,
                (logoHeight * ratio).roundToInt(), logoHeight.roundToInt()
            )

            return ImageSpan(logoDrawable, ImageSpan.ALIGN_CENTER)
        }

        @Throws(IOException::class)
        fun readInputStream(inputStream: InputStream): String {
            val r = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
            val total = StringBuilder()
            var line: String?
            while (r.readLine().also { line = it } != null) {
                total.append(line).append('\n')
            }
            return total.toString()
        }

        fun replacePlaceholders(text: String, map: Map<String, String>?): String {
            var text = text
            for (o in map!!.entries) {
                val placeholder: String = PLACEHOLDER_START + o.key + PLACEHOLDER_END
                text = text.replace(placeholder, o.value)
            }
            return text
        }

    }

}