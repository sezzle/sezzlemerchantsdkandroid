package com.sezzle.sezzlemerchantsdkandroid

class SezzleConstants {
    companion object {
        const val SDK_NAME = "Sezzle"

        // Default location is US
        var location: Sezzle.Location = Sezzle.Location.US;

        // URL
        const val SANDBOX_URL = "sandbox.sezzle.com"
        const val PRODUCTION_URL = "gateway.sezzle.com"

        const val CHECKOUT_PATH = "/v2/session"

        const val SEZZLE_CHECKOUT_CONFIRMATION_URL = "sezzle://checkout/confirmed"
        const val SEZZLE_CHECKOUT_CANCELLATION_URL = "sezzle://checkout/cancelled"

        const val TAG_CHECKOUT = "CHECKOUT"
        const val CHECKOUT_TOKEN = "checkout_token"
        const val INVALID_SEZZLE_TOKEN = "invalid_sezzle_token";

        const val HTTPS_PROTOCOL = "https://"
        const val HTTP_PROTOCOL = "http://"
        const val HTTP = "http"
        const val CONTENT_TYPE = "application/json; charset=utf-8"
        const val UTF_8 = "utf-8";
        const val X_SEZZLE_REQUEST_ID = "X-Sezzle-Request-Id"
        const val CUSTOMER_EXTRA = "customer_extra"
        const val ORDER_EXTRA = "order_extra"
        const val CHECKOUT_ERROR = "checkout_error"
        const val RESULT_ERROR = -8575
    }
}