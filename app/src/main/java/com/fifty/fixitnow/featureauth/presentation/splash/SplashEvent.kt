package com.fifty.fixitnow.featureauth.presentation.splash

import com.fifty.fixitnow.featureauth.presentation.onboarding.OnBoardingEvent

sealed class SplashEvent {
    object SplashLoadingComplete : SplashEvent()
}
