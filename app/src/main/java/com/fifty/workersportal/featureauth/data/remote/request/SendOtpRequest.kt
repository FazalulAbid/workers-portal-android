package com.fifty.workersportal.featureauth.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @SerializedName("countrycode")
    val countryCode: String,
    @SerializedName("phonenumber")
    val phoneNumber: String
)