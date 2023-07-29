package com.fifty.fixitnow.featureauth.presentation.onboarding

sealed class OnBoardingEvent {
    object SplashLoadingComplete : OnBoardingEvent()
}
