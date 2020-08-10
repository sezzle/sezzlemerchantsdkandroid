package com.sezzle.sezzlemerchantsdkandroid

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


abstract class SezzleActivity: AppCompatActivity(), SezzleWebChromeClient.Callback  {

    var container: ViewGroup? = null
    var webView: WebView? = null
    var progressIndicator: View? = null
    var sharedPref: SharedPreferences? = null


    abstract fun initViews()

    abstract fun beforeOnCreate()

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun onAttached()

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sezzle_activity_webview)
        container = window.decorView.findViewById(android.R.id.content)
        webView = findViewById(R.id.webview)
        progressIndicator = findViewById(R.id.progressIndicator)
        initViews()
        initData(savedInstanceState)
        onAttached()
        sharedPref = getSharedPreferences(SezzleConstants.SDK_NAME, MODE_PRIVATE);
    }

    override fun onDestroy() {
        container!!.removeView(webView)
        webView!!.removeAllViews()
        webView!!.clearCache(true)
        webView!!.clearHistory()
        webView!!.destroy()
        webView = null
        super.onDestroy()
    }

    override fun chromeLoadCompleted() {
        progressIndicator!!.visibility = View.GONE
    }
}