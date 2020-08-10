package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.android.parcel.Parcelize

/**
 * An *address*.
 *
 * This is a data class model for address
 *
 * @param name Name of the individual.
 * @param street Valid U.S. street address, verified by public address service APIs.
 * @param street2 Apartment, suite, floor, etc.
 * @param city City name, verified by public address service APIs.
 * @param state 2-letter ISO code or full name, verified by public address service APIs.
 * @param postal_code Must match other provided address information, verified by public address service APIs.
 * @param country_code If provided, must be 'US' or 'CA (3-letter ISO code).
 * @param phone_number Phone number of the individual.
 * @constructor Creates an address.
 */
@Parcelize
data class Address(@Nullable val name: String,
                   val street: String,
                   @Nullable val street2: String,
                   val city: String,
                   val state: String,
                   val postal_code: String,
                   val country_code: String,
                   @Nullable val phone_number: String): Parcelable