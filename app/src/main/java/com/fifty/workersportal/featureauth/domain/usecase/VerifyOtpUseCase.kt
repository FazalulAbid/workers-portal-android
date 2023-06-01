package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository

class VerifyOtpUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): SimpleResource {
        if (otpCode.length != Constants.OTP_RESEND_INTERVAL || !otpCode.matches(Regex("\\d+"))) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_a_valid_otp))
        }
        return repository.verifyOtp(
            countryCode = countryCode,
            phoneNumber = phoneNumber,
            otpCode = otpCode
        )
    }
}