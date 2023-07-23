package com.fifty.fixitnow.featurehistory.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun SelectableChip(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSecondary,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
    label: String,
    isSelected: Boolean,
    onSelectedChange: (Boolean) -> Unit
) {
    Text(
        text = label,
        style = textStyle,
        color = if (isSelected) selectedTextColor else unselectedTextColor,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (isSelected) {
                    selectedColor
                } else unselectedColor
            )
            .clickable {
                onSelectedChange(!isSelected)
            }
            .padding(vertical = SizeSmall, horizontal = SizeMedium)
    )
}