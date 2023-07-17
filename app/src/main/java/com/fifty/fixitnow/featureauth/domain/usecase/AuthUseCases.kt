package com.fifty.fixitnow.featureauth.domain.usecase

data class AuthUseCases(
    val getOtp: GetOtpUseCase,
    val verifyOtp: VerifyOtpUseCase
)
