package com.fifty.fixitnow.core.presentation.component

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    sheetContent: @Composable () -> Unit
) {
    ModalBottomSheet(
        containerColor = containerColor,
        onDismissRequest = onDismiss,
        tonalElevation = SizeMedium,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        sheetContent()
    }
}
