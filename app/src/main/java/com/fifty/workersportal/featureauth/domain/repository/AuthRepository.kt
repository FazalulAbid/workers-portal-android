package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource

    suspend fun verifyOtp(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): SimpleResource
}