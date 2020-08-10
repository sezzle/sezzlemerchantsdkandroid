package com.sezzle.sezzlemerchantsdkandroid

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.beust.klaxon.Klaxon
import com.sezzle.sezzlemerchantsdkandroid.exception.APIException
import com.sezzle.sezzlemerchantsdkandroid.exception.ConnectionException
import com.sezzle.sezzlemerchantsdkandroid.exception.SezzleException
import com.sezzle.sezzlemerchantsdkandroid.model.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class CheckoutRequest(private val customer: Customer, private val order: Order, val callback: InnerCheckoutCallback?) : SezzleRequest {
    private var checkoutCall: Call? = null

    override fun create() {
        val confirmUrl = URL(SezzleConstants.SEZZLE_CHECKOUT_CONFIRMATION_URL, "GET")
        val cancelUrl = URL(SezzleConstants.SEZZLE_CHECKOUT_CANCELLATION_URL, "GET")
        val checkout = Checkout(cancelUrl, confirmUrl, customer, order)
        val checkoutJson = Klaxon().toJsonString(checkout)

        if (checkoutCall != null) {
            checkoutCall?.cancel()
        }

        checkoutCall = SezzlePlugins.get()?.restClient()?.getCallForRequest(
          SezzleHttpRequest(url = SezzleHttpClient.getProtocol() + SezzlePlugins.get()?.baseUrl() + SezzleConstants.CHECKOUT_PATH,
              method = SezzleHttpRequest.Method.POST,
              body = SezzleHttpBody(SezzleConstants.CONTENT_TYPE, checkoutJson),
              tag = SezzleConstants.TAG_CHECKOUT)
        );

        checkoutCall?.enqueue(object : Callback {
            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                val responseBody = response.body
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        val checkoutResponse = Klaxon()
                            .parse<CheckoutResponse>(responseBody.string())
                        if (callback != null) {
                            Handler(Looper.getMainLooper()).post {
                                callback.onSuccess(
                                    checkoutResponse
                                )
                            }
                        }
                    } else {
                        handleErrorResponse(APIException("i/o failure", null))
                    }
                } else {
                    val sezzleException: SezzleException = SezzleHttpClient.createExceptionAndTrackFromResponse(
                            call.request(),
                            response,
                            responseBody
                        )
                    handleErrorResponse(sezzleException)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                handleErrorResponse(ConnectionException("i/o failure", e))
            }
        })
    }

    override fun cancel() {
        if (checkoutCall != null) {
            checkoutCall?.cancel()
            checkoutCall = null
        }
    }

    private fun handleErrorResponse(e: SezzleException) {
        SezzleLog.e(e.toString())
        if (callback != null) {
            Handler(Looper.getMainLooper())
                .post { callback.onError(e) }
        }
    }
}