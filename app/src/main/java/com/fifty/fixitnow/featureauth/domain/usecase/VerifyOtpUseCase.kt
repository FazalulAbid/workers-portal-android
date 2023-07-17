package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.model.OtpVerification
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository

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