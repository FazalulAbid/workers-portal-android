package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    containerColor: Color = MaterialTheme.colorScheme.background,
    onDismiss: () -> Unit,
    sheetContent: @Composable () -> Unit
) {
    when {
        sheetState.isVisible -> {
            ModalBottomSheet(
                onDismissRequest = onDismiss,
                modifier = modifier.fillMaxSize(),
                containerColor = containerColor,
                tonalElevation = SizeMedium,
            ) {
                sheetContent()
            }
        }
    }
}