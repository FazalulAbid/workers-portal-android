package com.fifty.workersportal.featureauth.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("_id")
    val id: String?,
    val age: Int?,
    val bio: String?,
    val countryCode: String?,
    val dailyWage: Int?,
    val firstName: String?,
    val gender: String?,
    val hourlyWage: Int?,
    val isWorker: Boolean?,
    val lastName: String?,
    val openToWork: Boolean?,
    val primarySkill: String?,
    val profilePicture: String?,
    val sampleWorkImages: List<String>?,
    val skillsList: List<String>?,
    val status: Boolean?,
    @SerializedName("__v")
    val versionKey: Int?,
)