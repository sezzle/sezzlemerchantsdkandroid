package com.sezzle.sezzlemerchantsdkandroid.exception

class ConnectionException : SezzleException {
    constructor(message: String?) : this(message, null)

    constructor(message: String?, e: Throwable?) : super(null, message, null, e)
}