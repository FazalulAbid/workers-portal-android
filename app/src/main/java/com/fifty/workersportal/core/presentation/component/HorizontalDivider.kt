package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color,
    thickness: Dp = SmallStrokeThickness
) {
    Divider(
        modifier = Modifier.height(thickness),
        color = color
    )
}