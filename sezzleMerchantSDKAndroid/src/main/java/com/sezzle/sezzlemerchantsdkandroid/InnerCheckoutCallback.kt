package com.sezzle.sezzlemerchantsdkandroid

import com.sezzle.sezzlemerchantsdkandroid.exception.SezzleException
import com.sezzle.sezzlemerchantsdkandroid.model.CheckoutResponse

interface InnerCheckoutCallback {
    fun onError(exception: SezzleException)
    fun onSuccess(response: CheckoutResponse?)
}