package com.sezzle.sezzlemerchantsdkandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTokenize(val token: String, val expiration: String, val approval_url: String, val links: List<ResponseLink>) :
    Parcelable