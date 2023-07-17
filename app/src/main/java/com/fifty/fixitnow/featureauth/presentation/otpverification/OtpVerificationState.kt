package com.fifty.fixitnow.featureauth.presentation.otpverification

data class OtpVerificationState(
    val countryCode: String = "",
    val phoneNumber: String = ""
)
