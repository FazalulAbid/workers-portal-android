package com.fifty.fixitnow.featureauth.presentation.auth

sealed class AuthEvent {
    data class EnteredPhoneNumber(val phoneNumber: String) : AuthEvent()
    data class OnGoogleSignIn(val email: String, val displayName: String) : AuthEvent()
    object ClearPhoneNumber : AuthEvent()
    object GetOtp : AuthEvent()
}
