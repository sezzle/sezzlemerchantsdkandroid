package com.sezzle.sezzlemerchantsdkandroid

import android.webkit.WebView

class ModalWebViewClient(private val callbacks: Callbacks) :
    SezzleWebViewClient(callbacks) {

    override fun hasCallbackUrl(view: WebView?, url: String?): Boolean {
        if (url != null && url!!.contains(SezzleConstants.SEZZLE_CHECKOUT_CANCELLATION_URL)) {
            callbacks.onWebViewCancellation()
            return true
        }
        return false
    }

    interface Callbacks : WebViewClientCallbacks {
        fun onWebViewCancellation()
    }
}