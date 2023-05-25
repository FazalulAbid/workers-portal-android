package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository

class VerifyOtpUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String,
        otp: String
    ): SimpleResource {
        return repository.verifyOtp(
            countryCode = countryCode,
            phoneNumber = phoneNumber,
            otp = otp
        )
    }
}