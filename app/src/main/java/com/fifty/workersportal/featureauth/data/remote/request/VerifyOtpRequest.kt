package com.fifty.workersportal.featureauth.data.remote.request

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("countryCode")
    val countryCode: String,
    val otpCode: String,
    @SerializedName("phone")
    val phoneNumber: String
)