package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SuccessDialogBoxHeight
import com.fifty.workersportal.core.presentation.ui.theme.SuccessDialogBoxWidth

@Composable
fun SuccessDialogContent(
    painter: Painter,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit = {}
) {
    Column(
        Modifier
            .background(
                MaterialTheme.colorScheme.background,
                MaterialTheme.shapes.large
            )
            .padding(SizeMedium)
            .height(SuccessDialogBoxHeight)
            .width(SuccessDialogBoxWidth),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .padding(horizontal = SizeLarge)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.checked_vector),
                contentDescription = null,
                Modifier
                    .size(ExtraLargeProfilePictureHeight)
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            SecondaryHeader(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = description, color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                ),
            )
        }
        PrimaryButton(text = buttonText, onClick = onButtonClick)
    }
}