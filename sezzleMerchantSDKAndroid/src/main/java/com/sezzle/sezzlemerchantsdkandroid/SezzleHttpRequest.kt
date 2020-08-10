package com.sezzle.sezzlemerchantsdkandroid

class SezzleHttpRequest(val url: String, val method: Method, val body: SezzleHttpBody, val tag: String) {

    enum class Method {
        GET, POST, PUT, DELETE
    }

}