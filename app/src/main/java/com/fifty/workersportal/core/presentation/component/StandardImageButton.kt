package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.MediumStrokeThickness

@Composable
fun StandardImageButton(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    outlineColor: Color = MaterialTheme.colorScheme.outline,
    imageIcon: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .border(
                MediumStrokeThickness,
                outlineColor,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(SizeMedium)
    ) {
        Image(
            painter = imageIcon,
            contentDescription = stringResource(
                R.string.sign_in_with_google
            )
        )
    }
}