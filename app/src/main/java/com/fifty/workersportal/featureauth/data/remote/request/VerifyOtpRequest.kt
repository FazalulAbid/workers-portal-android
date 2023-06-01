package com.fifty.workersportal.featureauth.data.remote.request

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("countrycode")
    val countryCode: String,
    val otpCode: String,
    @SerializedName("phonenumber")
    val phoneNumber: String
)