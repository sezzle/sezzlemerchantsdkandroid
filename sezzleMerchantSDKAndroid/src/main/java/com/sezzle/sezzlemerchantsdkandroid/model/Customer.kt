package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.android.parcel.Parcelize

/**
 * A *sezzle customer*.
 *
 * This is a data class model for customer
 *
 * @param tokenize Determines whether or not this customer will be tokenized.
 * @param email The customer’s email address.
 * @param first_name The customer’s first name.
 * @param last_name The customer’s last name.
 * @param phone The customer’s phone number.
 * @param dob The customer’s date of birth in YYYY-MM-DD format.
 * @param billing_address The customer’s billing address.
 * @param shipping_address The customer’s shipping address.
 */
@Parcelize
data class Customer(val tokenize: Boolean,
                   val email: String,
                   val first_name: String,
                   val last_name: String,
                   val phone: String,
                   val dob: String,
                   val billing_address: Address,
                   val shipping_address: Address) : Parcelable