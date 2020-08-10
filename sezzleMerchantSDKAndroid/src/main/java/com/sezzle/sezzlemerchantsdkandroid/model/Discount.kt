package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Discount from the merchant.
 *
 * This is a data class model for item
 *
 * @param name The description of the discount.
 * @param amount The price object.
 */
@Parcelize
data class Discount(val name : String,
                    val amount: Amount) : Parcelable