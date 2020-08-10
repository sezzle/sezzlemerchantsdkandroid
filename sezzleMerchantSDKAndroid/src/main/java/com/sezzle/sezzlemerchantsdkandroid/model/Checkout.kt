package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A *sezzle customer*.
 *
 * This is a data class model for customer
 *
 * @param cancel_url The HTTP request information used to redirect the customer in the case of a cancellation.
 * @param complete_url The HTTP request information used to redirect the customer upon completion of the session.
 * @param customer The customer for this session.
 * @param order The order for this session.
 */
@Parcelize
data class Checkout(val cancel_url: URL,
                 val complete_url: URL,
                 val customer: Customer,
                 val order: Order) : Parcelable