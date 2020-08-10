package com.sezzle.sezzlemerchantsdkandroid

import android.util.Log
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.SDK_NAME;


class SezzleLog {

    companion object {
        private var logLevel = Int.MAX_VALUE
        private const val TAG: String = SDK_NAME;

        fun getLogLevel(): Int {
            return logLevel;
        }

        fun setLogLevel(logLevel: Int) {
            this.logLevel = logLevel
        }

        private fun v(message: String, tr: Throwable?) {
            this.log(Log.VERBOSE, message, tr)
        }

        private fun d(message: String, tr: Throwable?) {
            this.log(Log.DEBUG, message, tr)
        }

        fun d(message: String) {
            d(message, null)
        }

        private fun i(message: String, tr: Throwable?) {
            this.log(Log.INFO, message, tr)
        }

        fun i(message: String) {
            i(message, null)
        }

        private fun w(message: String, tr: Throwable?) {
            this.log(Log.WARN, message, tr)
        }

        fun w(message: String) {
            w(message, null)
        }

        private fun e(message: String, tr: Throwable?) {
            this.log(Log.ERROR, message, tr)
        }

        fun e(message: String) {
            e(message, null)
        }

        private fun log(
            messageLogLevel: Int,
            message: String,
            tr: Throwable?
        ) {
            if (messageLogLevel >= logLevel) {
                if (tr == null) {
                    Log.println(messageLogLevel, TAG, message)
                } else {
                    Log.println(
                        messageLogLevel,
                        TAG,
                        """
                        $message
                        ${Log.getStackTraceString(tr)}
                        """.trimIndent()
                    )
                }
            }
        }
    }
}