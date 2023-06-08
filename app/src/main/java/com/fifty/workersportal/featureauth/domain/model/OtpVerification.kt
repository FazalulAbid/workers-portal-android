package com.fifty.workersportal.featureauth.domain.model

import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto

data class OtpVerification(
    val accessToken: String,
    val refreshToken: String,
    val user: ProfileDto
)
