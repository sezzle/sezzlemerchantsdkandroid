package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

class SezzleWebView: WebView {
    private val userAgentPrefix = ("Sezzle-SDK:Android-"
            + BuildConfig.VERSION_NAME);
    constructor(context: Context): super(context, null);

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        val userAgent: String =
            userAgentPrefix + " " + settings.userAgentString
        settings.userAgentString = userAgent
        clearCache(true)
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        isVerticalScrollBarEnabled = false
    }
}