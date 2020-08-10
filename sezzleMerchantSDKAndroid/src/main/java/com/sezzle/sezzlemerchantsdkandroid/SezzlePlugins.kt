package com.sezzle.sezzlemerchantsdkandroid

import android.util.Log
import android.webkit.CookieManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class SezzlePlugins(val configuration: Sezzle.Configuration) {

    companion object {
        private val LOCK = Any()
        private var instance: SezzlePlugins? = null

        fun initialize(configuration: Sezzle.Configuration) {
            this.set(SezzlePlugins(configuration))
        }

        fun set(plugins: SezzlePlugins) {
            synchronized(LOCK) {
                check(instance == null) { "SezzlePlugins is already initialized" }
                instance = plugins
            }
        }

        fun get(): SezzlePlugins? {
            synchronized(LOCK) { return instance }
        }

        fun reset() {
            synchronized(LOCK) { instance = null }
        }
    }

    private var restClient: SezzleHttpClient? = null

    fun publicKey(): String? {
        return configuration?.publicKey
    }

    fun environment(): Sezzle.Environment? {
        return configuration?.environment
    }

    fun environmentName(): String? {
        return configuration?.environment?.name
    }

    fun baseUrl(): String? {
        return configuration?.environment?.baseUrl()
    }

    @Synchronized
    fun restClient(): SezzleHttpClient? {
        if (restClient == null) {
            val clientBuilder = OkHttpClient.Builder()
            //add it as the first interceptor
            clientBuilder.interceptors()
                .add(0, Interceptor { chain: Interceptor.Chain ->
                    val builder = chain.request().newBuilder()
                    builder.addHeader("Accept", "application/json")
                    builder.addHeader("Content-Type", "application/json")
                    builder.addHeader("Sezzle-User-Agent", "Sezzle-Android-SDK")
                    builder.addHeader("Sezzle-User-Agent-Version", BuildConfig.VERSION_NAME)
                    val cookieManager =
                        CookieManager.getInstance()
                    val cookie = cookieManager
                        .getCookie(SezzleConstants.HTTPS_PROTOCOL.toString() + baseUrl())
                    if (cookie != null) {
                        builder.addHeader("Cookie", cookie)
                    }
                    chain.proceed(builder.build())
                })
            clientBuilder.connectTimeout(5, TimeUnit.SECONDS)
            clientBuilder.readTimeout(30, TimeUnit.SECONDS)
            clientBuilder.followRedirects(false)
            restClient = SezzleHttpClient.createClient(clientBuilder)
        }
        return restClient
    }
}