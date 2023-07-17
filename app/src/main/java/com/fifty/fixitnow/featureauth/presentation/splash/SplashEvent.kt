package com.fifty.fixitnow.featureauth.presentation.splash

sealed class SplashEvent {
    object CheckAuthentication : SplashEvent()
}
