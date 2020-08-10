package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseOrder(val uuid: String, val checkout_url: String, val intent: String, val links: List<ResponseLink>) :
    Parcelable