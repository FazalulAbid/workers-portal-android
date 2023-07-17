package com.fifty.fixitnow.core.presentation.util

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

const val offset = 700
const val duration = 300

val enterTransitionHorizontal =
    slideInHorizontally(
        initialOffsetX = { offset },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

val exitTransitionHorizontal =
    slideOutHorizontally(
        targetOffsetX = { -offset },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))

val popEnterTransitionHorizontal =
    slideInHorizontally(
        initialOffsetX = { -offset },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

val popExitTransitionHorizontal =
    slideOutHorizontally(
        targetOffsetX = { offset },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))

val enterTransitionVertical =
    slideInVertically(
        initialOffsetY = { offset },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

val exitTransitionVertical =
    slideOutVertically(
        targetOffsetY = { -offset },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))

val popEnterTransitionVertical =
    slideInVertically(
        initialOffsetY = { -offset },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

val popExitTransitionVertical =
    slideOutVertically(
        targetOffsetY = { offset },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))


