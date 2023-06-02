package com.fifty.workersportal.featureauth.presentation.splash

sealed class SplashEvent {
    object CheckAuthentication : SplashEvent()
}
