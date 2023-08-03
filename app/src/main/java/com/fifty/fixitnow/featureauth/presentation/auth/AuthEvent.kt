package com.fifty.fixitnow.featureauth.presentation.auth

sealed class AuthEvent {
    data class EnteredPhoneNumber(val phoneNumber: String) : AuthEvent()
    data class OnGoogleSignIn(val token: String?) : AuthEvent()
    object ClearPhoneNumber : AuthEvent()
    object GetOtp : AuthEvent()
}
