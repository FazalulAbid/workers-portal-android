package com.fifty.fixitnow.featureuser.domain.model

data class UpdateUserProfileData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val age: Int,
)