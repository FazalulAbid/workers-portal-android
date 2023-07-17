package com.fifty.fixitnow.core.presentation.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun AnimatedVisibilityWithFade(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val alpha: Float by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    if (visible) {
        Box(modifier = Modifier.alpha(alpha)) {
            content()
        }
    }
}