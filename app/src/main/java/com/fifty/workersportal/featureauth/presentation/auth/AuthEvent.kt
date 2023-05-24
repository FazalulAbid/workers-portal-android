package com.fifty.workersportal.featureauth.presentation.auth

sealed class AuthEvent {
    data class EnteredPhoneNumber(val phoneNumber: String) : AuthEvent()
    object ClearPhoneNumber : AuthEvent()
    object GetOtp : AuthEvent()
}
