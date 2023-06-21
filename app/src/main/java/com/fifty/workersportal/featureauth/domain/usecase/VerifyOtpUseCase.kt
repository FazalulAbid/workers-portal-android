package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.model.OtpVerification
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class VerifyOtpUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): Resource<OtpVerification> {
        if (otpCode.length != Constants.OTP_LENGTH || !otpCode.matches(Regex("\\d+"))) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_a_valid_otp))
        }
        val otpVerification = authRepository.verifyOtp(
            countryCode = countryCode,
            phoneNumber = phoneNumber,
            otpCode = otpCode
        ).data


        otpVerification?.let {
            return Resource.Success(data = it)
        } ?: return Resource.Error(UiText.unknownError())
    }
}