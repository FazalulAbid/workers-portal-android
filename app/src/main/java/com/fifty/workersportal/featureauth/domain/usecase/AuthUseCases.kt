package com.fifty.workersportal.featureauth.domain.usecase

data class AuthUseCases(
    val getOtp: GetOtpUseCase,
    val verifyOtp: VerifyOtpUseCase
)
