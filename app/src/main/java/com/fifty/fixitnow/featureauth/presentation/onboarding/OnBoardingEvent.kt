package com.fifty.fixitnow.featureauth.presentation.onboarding

sealed class OnBoardingEvent {
    data class OnBoardingScreenComplete(val value: Boolean) : OnBoardingEvent()
}
