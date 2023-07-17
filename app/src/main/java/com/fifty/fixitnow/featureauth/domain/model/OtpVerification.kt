package com.fifty.fixitnow.featureauth.domain.model

import com.fifty.fixitnow.featureuser.data.remote.dto.ProfileDto

data class OtpVerification(
    val accessToken: String,
    val refreshToken: String,
    val user: ProfileDto
)
