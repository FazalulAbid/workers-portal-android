package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource

interface AuthRepository {

    suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource

    suspend fun verifyOtp(
        countryCode: String,
        phoneNumber: String,
        otp: String
    ): SimpleResource
}