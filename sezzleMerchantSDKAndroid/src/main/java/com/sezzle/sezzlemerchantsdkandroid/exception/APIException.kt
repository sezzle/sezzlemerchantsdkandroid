package com.sezzle.sezzlemerchantsdkandroid.exception

import com.sezzle.sezzlemerchantsdkandroid.model.SezzleError

class APIException: SezzleException {

    constructor(statusCode: Int?,
                sezzleError: List<SezzleError>?,
                e: Throwable?
    ): super(sezzleError, null, statusCode, e)

    constructor(message: String?,
                e: Throwable?
    ): super(null, message, null, e)
}