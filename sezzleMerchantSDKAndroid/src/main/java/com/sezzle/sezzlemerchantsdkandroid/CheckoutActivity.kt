package com.sezzle.sezzlemerchantsdkandroid

import android.app.Activity
import android.content.Intent
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CHECKOUT_TOKEN
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CUSTOMER_EXTRA
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.INVALID_SEZZLE_TOKEN
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.ORDER_EXTRA
import com.sezzle.sezzlemerchantsdkandroid.exception.ConnectionException
import com.sezzle.sezzlemerchantsdkandroid.exception.SezzleException
import com.sezzle.sezzlemerchantsdkandroid.model.CheckoutResponse
import com.sezzle.sezzlemerchantsdkandroid.model.Customer
import com.sezzle.sezzlemerchantsdkandroid.model.Order

class CheckoutActivity: CheckoutBaseActivity(), CheckoutWebViewClient.Callbacks {

    companion object {
        fun startActivity(
            activity: Activity,
            requestCode: Int,
            customer: Customer,
            order: Order
        ) {
            val intent = Intent(activity, CheckoutActivity::class.java)
            intent.putExtra(CUSTOMER_EXTRA, customer)
            intent.putExtra(ORDER_EXTRA, order)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun initViews() {
        SezzleUtils.debuggableWebView(this)
        webView?.webViewClient = CheckoutWebViewClient(this)
        webView?.webChromeClient = SezzleWebChromeClient(this)
    }

    override fun getInnerCheckoutCallback(): InnerCheckoutCallback? {
        return object : InnerCheckoutCallback {
            override fun onError(exception: SezzleException) {
                finishWithError(exception)
            }

            override fun onSuccess(response: CheckoutResponse?) {
                sharedPref?.edit()?.putString(CHECKOUT_TOKEN, response?.order?.uuid)?.commit()
                webView?.loadUrl(response?.order?.checkout_url)
            }
        }
    }

    override fun onWebViewError(error: ConnectionException) {
        finishWithError(error)
    }

    override fun onWebViewConfirmation() {
        val intent = Intent()
        val token = sharedPref?.getString(CHECKOUT_TOKEN, INVALID_SEZZLE_TOKEN)
        intent.putExtra(CHECKOUT_TOKEN, token)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onWebViewCancellation() {
        webViewCancellation()
    }
}