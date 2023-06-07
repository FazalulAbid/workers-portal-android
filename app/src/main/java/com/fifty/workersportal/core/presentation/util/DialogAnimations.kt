package com.fifty.workersportal.core.presentation.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal const val ANIMATION_TIME_IN = 500L
internal const val ANIMATION_TIME_OUT = 300L
internal const val DIALOG_BUILD_TIME = 0L

@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(ANIMATION_TIME_IN.toInt())
        ),
        exit = scaleOut(
            animationSpec = tween(ANIMATION_TIME_OUT.toInt())
        ),
        content = content
    )
}

@Composable
fun AnimatedTransitionDialog(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (AnimatedTransitionDialogHelper) -> Unit
) {
    val onDismissSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
        launch {
            onDismissSharedFlow.asSharedFlow().collectLatest {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }
    }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }
    ) {
        Box(
            contentAlignment = contentAlignment,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value) {
                content(AnimatedTransitionDialogHelper(coroutineScope, onDismissSharedFlow))
            }
        }
    }
}

@Composable
fun AnimatedTransitionDialogEntryOnly(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    val animateTrigger = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
    }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(contentAlignment = contentAlignment,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value) {
                content()
            }
        }
    }
}

class AnimatedTransitionDialogHelper(
    private val coroutineScope: CoroutineScope,
    private val onDismissFlow: MutableSharedFlow<Any>
) {

    fun triggerAnimatedDismiss() {
        coroutineScope.launch {
            onDismissFlow.emit(Any())
        }
    }
}

suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateTrigger.value = false
    delay(ANIMATION_TIME_OUT)
    onDismissRequest()
}