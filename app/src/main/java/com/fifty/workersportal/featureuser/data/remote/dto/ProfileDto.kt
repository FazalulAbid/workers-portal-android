package com.fifty.workersportal.featureuser.data.remote.dto

import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
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
    val lastName: String,
    val openToWork: Boolean,
    val primaryCategory: WorkerCategory?,
    val profilePicture: String,
    val sampleWorkImages: List<String>?,
    val categoryList: List<WorkerCategory>?,
    val status: Boolean,
    @SerializedName("__v")
    val versionKey: Int?,
) {
    fun toUser(): UserSession {
        return UserSession(
            id = id,
            firstName = firstName,
            isWorker = isWorker,
            lastName = lastName
        )
    }

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
            lastName = lastName,
            openToWork = openToWork,
            primaryCategory = primaryCategory,
            profilePicture = profilePicture,
            sampleWorkImages = sampleWorkImages,
            categoryList = categoryList,
            status = status
        )
    }
}