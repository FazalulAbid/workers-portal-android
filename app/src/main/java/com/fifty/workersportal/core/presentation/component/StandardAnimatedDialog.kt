package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fifty.workersportal.core.presentation.util.AnimatedScaleInTransition
import com.fifty.workersportal.core.presentation.util.DIALOG_BUILD_TIME
import com.fifty.workersportal.core.presentation.util.startDismissWithExitAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StandardAnimatedDialog(
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onDismissRequest: () -> Unit,
    dialogContent: @Composable () -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
    }
    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value) {
                dialogContent()
            }
        }
    }
}