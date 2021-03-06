package com.sezzle.sezzlemerchantsdkandroid

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.HTTP;
import com.sezzle.sezzlemerchantsdkandroid.exception.ConnectionException


abstract class SezzleWebViewClient: WebViewClient {
    private var callbacks: WebViewClientCallbacks? = null
    abstract fun hasCallbackUrl(view: WebView?, url: String?): Boolean

    constructor(callbacks: WebViewClientCallbacks) {
        this.callbacks = callbacks;
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        return if (hasCallbackUrl(view, url)) {
            true
        } else !url.startsWith(HTTP)
    }

    // This method was deprecated in API level 23
    override fun onReceivedError(
        view: WebView?, errorCode: Int, description: String,
        failingUrl: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return
        }
        callbacks?.onWebViewError(ConnectionException("$errorCode, $description"))
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        // Please be aware that the new SDK 23 callback will be called for any resource
        // (iFrame, image, etc) that failed to load, not just for the main page
        if (request.isForMainFrame) {
            callbacks?.onWebViewError(
                ConnectionException(
                    error.errorCode.toString() + ", " + error.description.toString()
                )
            )
        }
    }

    interface WebViewClientCallbacks {
        fun onWebViewError(error: ConnectionException)
    }
}