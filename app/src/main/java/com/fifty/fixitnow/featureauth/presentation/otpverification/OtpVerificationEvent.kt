package com.fifty.fixitnow.featureauth.presentation.otpverification

sealed class OtpVerificationEvent {
    data class EnterOtp(val otp: String) : OtpVerificationEvent()
    object ResendOtp : OtpVerificationEvent()
    object VerifyOtp : OtpVerificationEvent()
}
