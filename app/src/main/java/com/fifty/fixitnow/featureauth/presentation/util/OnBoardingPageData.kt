package com.fifty.fixitnow.featureauth.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.ui.res.stringResource
import com.fifty.fixitnow.R

sealed class OnBoardingPageData(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPageData(
        image = R.drawable.welcome_image_one,
        title = "Welcome to Fix It Now!",
        description = "Find the Perfect Job for You, Find a Perfect Worker for You!"
    )

    object Second : OnBoardingPageData(
        image = R.drawable.welcome_image_two,
        title = "Hire Top Talent Near You!",
        description = "Easily Connect with Skilled Professionals for Your Projects."
    )

    object Third : OnBoardingPageData(
        image = R.drawable.welcome_image_three,
        title = "Join our Worker Community!",
        description = "Showcase Your Skills and Get Hired with Us."
    )

    object Fourth : OnBoardingPageData(
        image = R.drawable.welcome_image_four,
        title = "Empowering Your Journey",
        description = "We're Here to Bridge the Gap Between Workers and Work Seekers!"
    )
}
