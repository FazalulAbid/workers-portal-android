package com.fifty.workersportal.featureauth.presentation.otp

sealed class OtpVerificationEvent {
    data class EnterOtp(val otp: String) : OtpVerificationEvent()
    object ResendOtp : OtpVerificationEvent()
}
