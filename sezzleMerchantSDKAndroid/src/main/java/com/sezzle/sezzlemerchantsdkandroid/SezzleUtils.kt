package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import android.view.Window
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.LOGO_PLACEHOLDER
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

    }

}