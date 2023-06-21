package com.fifty.workersportal.featureuser.domain.model

import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

data class Profile(
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
    val isVerifiedWorker: Boolean,
    val lastName: String,
    val openToWork: Boolean,
    val primaryCategory: String?,
    val profilePicture: String,
    val sampleWorkImages: List<String>?,
    val categoryList: List<WorkerCategory>?,
    val status: Boolean,
) {
    fun toUserProfile(): UserProfile =
        UserProfile(
            id = id,
            age = age,
            email = email,
            firstName = firstName,
            gender = gender,
            lastName = lastName,
            profilePicture = profilePicture
        )
}
