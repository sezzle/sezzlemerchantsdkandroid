package com.sezzle.sezzlemerchantsdkandroid

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CHECKOUT_ERROR
import com.sezzle.sezzlemerchantsdkandroid.SezzleConstants.Companion.CHECKOUT_TOKEN
import com.sezzle.sezzlemerchantsdkandroid.model.Customer
import com.sezzle.sezzlemerchantsdkandroid.model.Order
import java.math.BigDecimal

class Sezzle {

    companion object {
        // log levels
        const val LOG_LEVEL_VERBOSE = Log.VERBOSE;
        const val LOG_LEVEL_DEBUG = Log.DEBUG;
        const val LOG_LEVEL_INFO = Log.INFO;
        const val LOG_LEVEL_WARNING = Log.WARN;
        const val LOG_LEVEL_ERROR = Log.ERROR;
        const val LOG_LEVEL_NONE = Integer.MAX_VALUE;
        const val RESULT_ERROR = -8575

        fun startCheckout(
            @NonNull activity: Activity,
            @NonNull customer: Customer,
            @NonNull order: Order
        ) {
            SezzleUtils.requireNonNull(activity, "activity cannot be null")
            SezzleUtils.requireNonNull(customer, "customer cannot be null")
            SezzleUtils.requireNonNull(order, "order cannot be null")
            CheckoutActivity.startActivity(
                activity,
                8001,
                customer,
                order
            )
        }

        fun initialize(configuration: Configuration) {
            SezzleUtils.requireNonNull(configuration, "configuration cannot be null")
            if (isInitialized()) {
                SezzleLog.w("Sezzle is already initialized")
                return
            }
            SezzlePlugins.initialize(configuration)
        }

        private fun isInitialized(): Boolean {
            return SezzlePlugins.get() != null
        }

        fun handleCheckoutData(
            callbacks: CheckoutCallbacks,
            requestCode: Int,
            resultCode: Int,
            data: Intent?
        ): Boolean {
            SezzleUtils.requireNonNull(callbacks, "CheckoutCallbacks cannot be null")
            if (requestCode == 8001) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        SezzleUtils.requireNonNull(data, "data cannot be null")
                        callbacks.onSezzleCheckoutSuccess(data!!.getStringExtra(CHECKOUT_TOKEN))
                    }
                    Activity.RESULT_CANCELED -> callbacks.onSezzleCheckoutCancelled()
                    this.RESULT_ERROR -> {
                        SezzleUtils.requireNonNull(data, "data cannot be null")
                        callbacks.onSezzleCheckoutError(data?.getStringExtra(CHECKOUT_ERROR))
                    }
                    else -> {
                    }
                }
                return true
            }
            return false
        }

        fun showSiteModal(
            activity: Activity,
            modalVersion: String?,
            language: String?
        ) {
            SezzleUtils.requireNonNull(activity, "activity cannot be null")
            ModalActivity.startActivity(
                activity, 0,
                modalVersion, language
            )
        }
    }

    interface CheckoutCallbacks {
        fun onSezzleCheckoutError(message: String?)

        fun onSezzleCheckoutCancelled()

        fun onSezzleCheckoutSuccess(token: String)
    }

    enum class Location {
        US, CA
    }

    enum class Environment {
        SANDBOX, PRODUCTION;

        open fun baseUrl(): String? {
            return when (this) {
                SANDBOX -> SezzleConstants.SANDBOX_URL
                else -> SezzleConstants.PRODUCTION_URL
            }
        }
    }

    class Configuration(
        val publicKey: String,
        val environment: Environment,
        val logLevel: Int,
        val location: Location
    )

}