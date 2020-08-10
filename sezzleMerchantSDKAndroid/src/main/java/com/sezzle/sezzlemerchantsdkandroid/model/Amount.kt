package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Amount object
 *
 * The price object is used for items, discounts, shiping amount, tax amount, and order amount.
 *
 * @param amount_in_cents The amount of the item in cents.
 * @param currency The 3 character currency code as defined by ISO 4217.
 */
@Parcelize
data class Amount(
    val amount_in_cents: Int,
    val currency: String): Parcelable