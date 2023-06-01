package com.fifty.workersportal.featureauth.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("_id")
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    @SerializedName("profilePicture")
    val profilePictureUrl: String,
    val dailyWage: Float,
    val hourlyWage: Float,
    val primarySkill: String?,
    val openToWork: Boolean,
    val fname: String,
    val lname: String,
    val mobile: String,
    val countryCode: String,
    @SerializedName("status")
    val isActive: Boolean,
    val isWorker: Boolean,
    @SerializedName("__v")
    val versionKey: Int
)