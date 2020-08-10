package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * An item in cart.
 *
 * This is a data class model for item
 *
 * @param name The name of the item.
 * @sku email The sku identifier.
 * @param quantity The quantity purchased.
 * @param price The price object.
 */
@Parcelize
data class Item(
    val name: String,
    val sku: String,
    val quantity: Int,
    val price: Amount) : Parcelable