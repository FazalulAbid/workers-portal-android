package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium

@Composable
fun SecondaryHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    fontWeight: FontWeight = FontWeight.Medium
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SizeMedium),
        text = text,
        style = style.copy(
            fontWeight = fontWeight,
            color = color
        )
    )
}