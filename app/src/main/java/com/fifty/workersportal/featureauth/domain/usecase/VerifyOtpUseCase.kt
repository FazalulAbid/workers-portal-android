package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class VerifyOtpUseCase(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): SimpleResource {
        if (otpCode.length != Constants.OTP_LENGTH || !otpCode.matches(Regex("\\d+"))) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_a_valid_otp))
        }
        val otpVerification = authRepository.verifyOtp(
            countryCode = countryCode,
            phoneNumber = phoneNumber,
            otpCode = otpCode
        ).data


        otpVerification?.let {
            sessionRepository.saveAccessToken(it.accessToken)
            sessionRepository.saveRefreshToken(it.refreshToken)
            sessionRepository.saveUserSession(
                UserSession(
                    id = it.user.id,
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    isWorker = it.user.isWorker
                )
            )
            return Resource.Success(Unit)
        } ?: return Resource.Error(UiText.unknownError())
    }
}