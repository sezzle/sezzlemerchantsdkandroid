package com.sezzle.sezzlemerchantsdkandroid.exception

import com.sezzle.sezzlemerchantsdkandroid.model.SezzleError
import java.lang.Exception

abstract class SezzleException(private val sezzleError: List<SezzleError>?, override val message: String?,
                               private val statusCode: Int?, val e: Throwable?): Exception() {

    override fun toString(): String {
        var errorString = ""
        if (sezzleError != null) {
            errorString = "$errorString,  error: $sezzleError"
        }
        if (message != null) {
            errorString = "$errorString, message: $message"
        }
        if (statusCode != null) {
            errorString = "$errorString, status_code: $statusCode"
        }
        return errorString
    }
}