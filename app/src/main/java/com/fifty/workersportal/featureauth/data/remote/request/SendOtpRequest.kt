package com.fifty.workersportal.featureauth.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("mobile")
    val phoneNumber: String
)