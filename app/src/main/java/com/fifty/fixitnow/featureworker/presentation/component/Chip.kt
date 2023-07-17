package com.fifty.fixitnow.featureworker.presentation.component

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
fun Chip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selected: Boolean = false,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSecondary,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
    onChipClick: () -> Unit
) {
    Text(
        text = text,
        style = textStyle,
        color = if (selected) selectedTextColor else unselectedTextColor,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (selected) {
                    selectedColor
                } else unselectedColor
            )
            .clickable {
                if (onChipClick != null) {
                    onChipClick()
                }
            }
            .padding(vertical = SizeSmall, horizontal = SizeMedium)
    )
}

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selected: Boolean = false,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSecondary,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
) {
    Text(
        text = text,
        style = textStyle,
        color = if (selected) selectedTextColor else unselectedTextColor,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (selected) {
                    selectedColor
                } else unselectedColor
            )
            .padding(vertical = SizeSmall, horizontal = SizeMedium)
    )
}