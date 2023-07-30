package com.fifty.fixitnow.featureauth.presentation.util

import androidx.annotation.RawRes
import com.fifty.fixitnow.R

sealed class OnBoardingPageData(
    @RawRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPageData(
        image = R.raw.done_lottie,
        title = "Welcome to ${R.string.app_name}",
        description = "Find the Perfect Job for You!"
    )

    object Second : OnBoardingPageData(
        image = R.raw.done_lottie,
        title = "Hire Top Talent Near You!",
        description = "Easily Connect with Skilled Professionals for Your Projects."
    )

    object Third : OnBoardingPageData(
        image = R.raw.done_lottie,
        title = "Join our Worker Community!",
        description = "Showcase Your Skills and Get Hired with Us."
    )

    object Fourth : OnBoardingPageData(
        image = R.raw.done_lottie,
        title = "Empowering Your Journey",
        description = "We're Here to Bridge the Gap Between Workers and Work Seekers!"
    )
}
