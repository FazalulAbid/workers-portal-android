package com.fifty.workersportal.featureauth.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("verification")
    val verification: Boolean,
    @SerializedName("accesstoken")
    val accessToken: String,
    @SerializedName("refreshtoken")
    val refreshToken: String
)