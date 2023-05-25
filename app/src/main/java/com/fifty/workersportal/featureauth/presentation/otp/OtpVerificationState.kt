package com.fifty.workersportal.featureauth.presentation.otp

data class OtpVerificationState(
    val countryCode: String = "",
    val phoneNumber: String = ""
)
