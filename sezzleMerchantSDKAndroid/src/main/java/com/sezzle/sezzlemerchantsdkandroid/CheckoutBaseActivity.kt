package com.sezzle.sezzlemerchantsdkandroid

import android.content.Intent
import android.os.Bundle
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CHECKOUT_ERROR
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CUSTOMER_EXTRA
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.ORDER_EXTRA
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.RESULT_ERROR
import com.sezzle.sezzlemerchantsdkandroid.model.Customer
import com.sezzle.sezzlemerchantsdkandroid.model.Order

abstract class CheckoutBaseActivity: SezzleActivity() {

    private var checkoutRequest: CheckoutRequest? = null;
    private var customer: Customer? = null
    private var order: Order? = null
    abstract fun getInnerCheckoutCallback(): InnerCheckoutCallback?

    override fun beforeOnCreate() {
        SezzleUtils.hideActionBar(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            customer = savedInstanceState.getParcelable(CUSTOMER_EXTRA)
            order = savedInstanceState.getParcelable(ORDER_EXTRA)
        } else {
            customer = intent.getParcelableExtra(CUSTOMER_EXTRA)
            order = intent.getParcelableExtra(ORDER_EXTRA)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(CUSTOMER_EXTRA, customer)
        outState.putParcelable(ORDER_EXTRA, order)
    }

    override fun onAttached() {
        checkoutRequest = CheckoutRequest(customer!!, order!!, getInnerCheckoutCallback())
        checkoutRequest?.create()
    }

    override fun onDestroy() {
        checkoutRequest?.cancel()
        super.onDestroy()
    }

    protected open fun finishWithError(error: Throwable) {
        val intent = Intent()
        intent.putExtra(CHECKOUT_ERROR, error.toString())
        setResult(RESULT_ERROR, intent)
        finish()
    }

    protected open fun webViewCancellation() {
        setResult(RESULT_CANCELED)
        finish()
    }
}