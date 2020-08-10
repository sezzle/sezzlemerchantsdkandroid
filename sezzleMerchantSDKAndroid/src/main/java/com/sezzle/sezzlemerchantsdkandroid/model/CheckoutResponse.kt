package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckoutResponse(val uuid: String, val links: List<ResponseLink>, val order: ResponseOrder, val tokenize: ResponseTokenize) : Parcelable