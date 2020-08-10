package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.view.Window
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

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

    }

}