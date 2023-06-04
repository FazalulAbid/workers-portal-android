package com.fifty.workersportal.featureauth.domain.model

import com.fifty.workersportal.featureauth.data.remote.response.UserResponse

data class OtpVerification(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)
