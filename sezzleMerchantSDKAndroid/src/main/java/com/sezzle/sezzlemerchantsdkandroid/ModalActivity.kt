package com.sezzle.sezzlemerchantsdkandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CANCEL_URL
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.HTTPS_PROTOCOL
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.LANGUAGE
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.MAP_EXTRA
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.MODAL_VERSION
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.PREQUAL_ERROR
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.RESULT_ERROR
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.SEZZLE_CHECKOUT_CANCELLATION_URL
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.TEXT_HTML
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.UTF_8
import com.sezzle.sezzlemerchantsdkandroid.exception.ConnectionException
import java.io.IOException
import java.math.BigDecimal
import java.util.*

class ModalActivity: SezzleActivity(), ModalWebViewClient.Callbacks {

    private var map: HashMap<String, String>? = null

    companion object {
        fun startActivity(
            activity: Activity, requestCode: Int,
            modalVersion: String?,
            language: String?
        ) {
            val intent = Intent(activity, ModalActivity::class.java)
            val map = HashMap<String, String>()
            map[CANCEL_URL] = SEZZLE_CHECKOUT_CANCELLATION_URL
            map[MODAL_VERSION] = modalVersion ?: "2.0.0"
            map[LANGUAGE] = language ?: "en"
            intent.putExtra(MAP_EXTRA, map)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun beforeOnCreate() {
        SezzleUtils.hideActionBar(this)
    }

    override fun initViews() {
        SezzleUtils.debuggableWebView(this)
        webView?.webViewClient = ModalWebViewClient(this)
        webView?.webChromeClient = SezzleWebChromeClient(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        map = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(MAP_EXTRA) as HashMap<String, String>?
        } else {
            intent.getSerializableExtra(MAP_EXTRA) as HashMap<String, String>?
        }
    }

    override fun onAttached() {
        val html = initialHtml()
        webView?.loadDataWithBaseURL(
            HTTPS_PROTOCOL + SezzlePlugins.get()?.baseUrl(),
            html, TEXT_HTML, UTF_8, null
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(MAP_EXTRA, map)
    }

    private fun initialHtml(): String? {
        val html: String
        html = try {
            val ins = resources.openRawResource(R.raw.sezzle_modal_template)
            SezzleUtils.readInputStream(ins)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return SezzleUtils.replacePlaceholders(html, map)
    }

    override fun onWebViewCancellation() {
        setResult(RESULT_CANCELED)
        finish()
    }

    override fun onWebViewError(error: ConnectionException) {
        finish()
        val intent = Intent()
        intent.putExtra(PREQUAL_ERROR, error.toString())
        setResult(RESULT_ERROR, intent)
        finish()
    }

}