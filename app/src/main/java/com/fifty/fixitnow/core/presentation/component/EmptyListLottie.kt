package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight

@Composable
fun EmptyListLottie(
    size: Dp = ExtraExtraLargeProfilePictureHeight
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_box_lottie)
    )
    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieComposition, iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = Modifier.size(size),
        composition = lottieComposition,
        progress = lottieProgress
    )
}