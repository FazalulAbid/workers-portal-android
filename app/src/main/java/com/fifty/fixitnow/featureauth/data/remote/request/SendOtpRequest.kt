package com.fifty.fixitnow.featureauth.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("phone")
    val phoneNumber: String
)