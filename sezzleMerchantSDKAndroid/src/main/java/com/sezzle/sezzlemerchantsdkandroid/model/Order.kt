package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.android.parcel.Parcelize

/**
 * A *sezzle customer*.
 *
 * This is a data class model for customer
 *
 * @param reference_id Determines whether or not this customer will be tokenized.
 * @param description The customer’s email address.
 * @param order_amount The customer’s first name.
 * @param requires_shipping_info The customer’s last name.
 * @param items The customer’s phone number.
 * @param discounts The customer’s date of birth in YYYY-MM-DD format.
 * @param metadata The customer’s billing address.
 * @param shipping_amount The customer’s shipping address.
 * @param tax_amount The customer’s shipping address.
 * @param checkout_expiration The customer’s shipping address.
 */
@Parcelize
data class Order(val intent: String,
                 val reference_id: String,
                 val description: String,
                 val order_amount: Amount,
                 @Nullable val requires_shipping_info: Boolean?,
                 @Nullable val items: List<Item>?,
                 @Nullable val discounts: List<Discount>?,
                 @Nullable val metadata: Map<String, String>?,
                 @Nullable val shipping_amount: Amount?,
                 @Nullable val tax_amount: Amount?,
                 @Nullable val checkout_expiration: String?) : Parcelable