package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium

@Composable
fun PrimaryHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = SizeMedium)) {
        Text(
            modifier = modifier
                .fillMaxWidth(0.5f),
            text = text,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = color,
            )
        )
    }
}