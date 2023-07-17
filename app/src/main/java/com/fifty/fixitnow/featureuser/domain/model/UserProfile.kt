package com.fifty.fixitnow.featureuser.domain.model

data class UserProfile(
    val id: String,
    val age: Int,
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val isWorker: Boolean,
    val profilePicture: String,
    val selectedAddress: String?,
)
