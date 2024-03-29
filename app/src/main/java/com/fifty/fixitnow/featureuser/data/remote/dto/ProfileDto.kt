package com.fifty.fixitnow.featureuser.data.remote.dto

import com.fifty.fixitnow.featureuser.domain.model.Profile
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("_id")
    val id: String,
    val age: Int,
    val bio: String,
    val countryCode: String,
    val phone: String,
    val email: String,
    val dailyWage: Float,
    val firstName: String,
    val gender: String,
    val hourlyWage: Float,
    val isWorker: Boolean,
    @SerializedName("isVerified")
    val isVerifiedWorker: Boolean,
    val lastName: String,
    val openToWork: Boolean,
    val primaryCategory: String?,
    val profilePicture: String,
    val categoryList: List<WorkerCategory>?,
    val selectedAddress: String?,
    val identityUrl: String?,
    val status: Boolean,
    @SerializedName("__v")
    val versionKey: Int?,
) {
    fun toProfile(): Profile {
        return Profile(
            id = id,
            age = age,
            bio = bio,
            countryCode = countryCode,
            phone = phone,
            email = email,
            dailyWage = dailyWage,
            firstName = firstName,
            gender = gender,
            hourlyWage = hourlyWage,
            isWorker = isWorker,
            isVerifiedWorker = isVerifiedWorker,
            lastName = lastName,
            openToWork = openToWork,
            primaryCategory = primaryCategory,
            profilePicture = profilePicture,
            categoryList = categoryList,
            status = status,
            selectedAddress = selectedAddress,
            identityUrl = identityUrl
        )
    }
}