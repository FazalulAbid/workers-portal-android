package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.domain.model.OtpVerification

interface AuthRepository {

    suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource

    suspend fun verifyOtp(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): Resource<OtpVerification>

    suspend fun authenticate(): SimpleResource
}