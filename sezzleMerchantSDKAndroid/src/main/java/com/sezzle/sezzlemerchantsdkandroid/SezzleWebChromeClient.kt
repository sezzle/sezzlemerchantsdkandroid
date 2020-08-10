package com.sezzle.sezzlemerchantsdkandroid

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Message
import android.webkit.ConsoleMessage
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class SezzleWebChromeClient: WebChromeClient {
    private var callback: Callback? = null

    constructor(callback: Callback) {
        this.callback = callback;
    }

    interface Callback {
        fun chromeLoadCompleted()
    }

    override fun onCreateWindow(
        view: WebView, isDialog: Boolean, isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        val result = view.hitTestResult
        val data = result.extra
        val context = view.context
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
        context.startActivity(browserIntent)
        return false
    }

    override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
        if (BuildConfig.DEBUG) {
            SezzleLog.d(cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId())
            return true
        }
        return false
    }

    override fun onJsConfirm(
        view: WebView,
        url: String?,
        message: String?,
        result: JsResult
    ): Boolean {
        AlertDialog.Builder(view.context).setTitle(R.string.sezzle)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int -> result.confirm() }
            )
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int -> result.cancel() }
            )
            .create()
            .show()
        return true
    }

    override fun onProgressChanged(view: WebView?, progress: Int) {
        if (progress > 99) {
            callback?.chromeLoadCompleted()
        }
    }
}