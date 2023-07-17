package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SmallStrokeThickness

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline,
    thickness: Dp = SmallStrokeThickness,
    verticalPadding: Boolean = false
) {
    if (verticalPadding) Spacer(modifier = Modifier.height(SizeMedium))
    Divider(
        modifier = modifier.height(thickness),
        color = color
    )
    if (verticalPadding) Spacer(modifier = Modifier.height(SizeMedium))
}