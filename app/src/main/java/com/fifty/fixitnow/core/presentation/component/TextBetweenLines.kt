package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium

@Composable
fun TextBetweenLines(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    lineColor: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Divider(
            thickness = 2.dp,
            color = lineColor
        )
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SizeMedium),
            text = text,
            color = textColor
        )
    }
}