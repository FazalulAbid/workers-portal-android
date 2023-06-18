package com.fifty.workersportal.featureuser.domain.model

data class UserProfile(
    val id: String,
    val age: Int,
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val profilePicture: String
)
