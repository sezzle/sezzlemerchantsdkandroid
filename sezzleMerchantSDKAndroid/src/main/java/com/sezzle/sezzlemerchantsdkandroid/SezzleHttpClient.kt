package com.sezzle.sezzlemerchantsdkandroid

import android.util.Base64
import android.util.Log
import com.beust.klaxon.Klaxon
import com.sezzle.sezzlemerchantsdkandroid.exception.APIException
import com.sezzle.sezzlemerchantsdkandroid.exception.InvalidRequestException
import com.sezzle.sezzlemerchantsdkandroid.exception.SezzleException
import com.sezzle.sezzlemerchantsdkandroid.model.SezzleError
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.BufferedSink
import java.io.IOException

class SezzleHttpClient(var builder: OkHttpClient.Builder? = null) {
    private var okHttpClient: OkHttpClient? = null

    init {
        if (builder == null) {
            builder = OkHttpClient.Builder();
        }

        okHttpClient = builder?.build();
    }

    companion object {
        fun createClient(builder: OkHttpClient.Builder?): SezzleHttpClient? {
            return SezzleHttpClient(builder)
        }

        fun handleAPIError(
            sezzleError: List<SezzleError>,
            responseCode: Int
        ): SezzleException {
            return when (responseCode) {
                400 -> {
                    InvalidRequestException(
                        responseCode,
                        sezzleError,
                        null
                    )
                }
                else -> {
                    APIException(
                        responseCode,
                        sezzleError,
                        null
                    )
                }
            }
        }

        fun createExceptionAndTrackFromResponse(
            okHttpRequest: Request?,
            response: Response,
            responseBody: ResponseBody?
        ): SezzleException {
            if (responseBody != null && responseBody.contentLength() > 0) {
                val sezzleError = Klaxon().parseArray<SezzleError>(responseBody.string())
                return handleAPIError(
                    sezzleError!!,
                    response.code
                )
            }
            return APIException("Error getting exception from response", null)
        }

        fun getProtocol(): String {
            Log.v("Tag", SezzlePlugins.get()?.baseUrl()!!)
            return if (SezzlePlugins.get() != null && SezzlePlugins.get()?.baseUrl() != null && SezzlePlugins.get()?.baseUrl()!!.contains(SezzleConstants.HTTP)) {
                ""
            } else {
                SezzleConstants.HTTPS_PROTOCOL
            }
        }

        private class SezzleOkHttpRequestBody internal constructor(body: SezzleHttpBody) : RequestBody() {
            private val body: SezzleHttpBody = body
            private val content: ByteArray = body.content.toByteArray(Charsets.UTF_8)
            private val offset: Int = 0
            private val byteCount: Int
            override fun contentLength(): Long {
                return byteCount.toLong()
            }

            override fun contentType(): MediaType? {
                val contentType: String = body.contentType;
                return if (contentType == null) null else body.contentType.toMediaTypeOrNull()
            }

            @Throws(IOException::class)
            override fun writeTo(sink: BufferedSink) {
                sink.write(content, offset, byteCount)
            }

            init {
                byteCount = content.size
            }
        }
    }

    fun getCallForRequest(request: SezzleHttpRequest): Call? {
        return okHttpClient?.newCall(getRequest(request))
    }

    private fun getRequest(request: SezzleHttpRequest): Request {
        val okHttpRequestBuilder = Request.Builder()
        val method: SezzleHttpRequest.Method = request.method
        when (method) {
            SezzleHttpRequest.Method.GET -> okHttpRequestBuilder.get()
            SezzleHttpRequest.Method.DELETE, SezzleHttpRequest.Method.POST, SezzleHttpRequest.Method.PUT -> {
            }
            else -> throw IllegalStateException("Unsupported http method: " + method.toString())
        }
        // Set request url
        okHttpRequestBuilder.url(request.url)

        // Set request body
        val body: SezzleHttpBody = request.body
        var okHttpRequestBody: SezzleOkHttpRequestBody? = null
        if (body != null) {
            okHttpRequestBody = SezzleOkHttpRequestBody(body)
        }

        // set request tag
        okHttpRequestBuilder.tag(request.tag)
        if (okHttpRequestBody != null) {
            when (method) {
                SezzleHttpRequest.Method.PUT -> okHttpRequestBuilder.put(okHttpRequestBody)
                SezzleHttpRequest.Method.POST -> okHttpRequestBuilder.post(okHttpRequestBody)
                SezzleHttpRequest.Method.DELETE -> okHttpRequestBuilder.delete(okHttpRequestBody)
                else -> {
                }
            }
        }

        val publicKey = SezzlePlugins.get()?.configuration?.publicKey;
        okHttpRequestBuilder.addHeader("Authorization", "Basic ${Base64.encodeToString(publicKey?.toByteArray(), Base64.NO_WRAP)}")
        return okHttpRequestBuilder.build()
    }
}