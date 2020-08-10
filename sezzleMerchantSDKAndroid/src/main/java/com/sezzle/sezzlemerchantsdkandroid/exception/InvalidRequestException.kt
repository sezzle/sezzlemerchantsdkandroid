package com.sezzle.sezzlemerchantsdkandroid.exception

import com.sezzle.sezzlemerchantsdkandroid.model.SezzleError

class InvalidRequestException: SezzleException {

    constructor(statusCode: Int,
                sezzleError: List<SezzleError>,
                e: Throwable?
    ): super(sezzleError, null, statusCode, e)
}