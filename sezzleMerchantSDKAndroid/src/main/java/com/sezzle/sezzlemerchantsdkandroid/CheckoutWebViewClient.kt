package com.sezzle.sezzlemerchantsdkandroid

import android.content.SharedPreferences
import android.util.Log
import android.webkit.WebView
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CHECKOUT_TOKEN
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.SEZZLE_CHECKOUT_CANCELLATION_URL
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.SEZZLE_CHECKOUT_CONFIRMATION_URL


class CheckoutWebViewClient: SezzleWebViewClient {
    private var callbacks: Callbacks? = null
    var sharedPref: SharedPreferences? = null

    constructor(callbacks: Callbacks): super(callbacks) {
        this.callbacks = callbacks
    }

    override fun hasCallbackUrl(view: WebView?, url: String?): Boolean {
        if (url != null && url.contains(SEZZLE_CHECKOUT_CONFIRMATION_URL)) {
            callbacks?.onWebViewConfirmation()
            return true
        } else if (url != null && url.contains(SEZZLE_CHECKOUT_CANCELLATION_URL)) {
            callbacks?.onWebViewCancellation()
            return true
        }
        return false
    }

    interface Callbacks : WebViewClientCallbacks {
        fun onWebViewConfirmation()
        fun onWebViewCancellation()
    }
}