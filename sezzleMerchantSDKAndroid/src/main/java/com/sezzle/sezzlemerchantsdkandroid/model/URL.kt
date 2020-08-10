package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * URL object
 *
 * This is used for both the cancel and complete URL objects
 *
 * @param href The URL used when redirecting a customer.
 * @param method The HTTP request method used when redirecting a customer. Currently only the GET method is supported.
 */
@Parcelize
data class URL(val href : String,
                  val method: String) : Parcelable