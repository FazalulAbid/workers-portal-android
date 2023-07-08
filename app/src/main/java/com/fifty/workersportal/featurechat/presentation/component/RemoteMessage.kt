package com.fifty.workersportal.featurechat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun RemoteMessage(
    message: String,
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    textColor: Color = Color.White,
    formattedTime: String
) {
    val cornerRadius = MaterialTheme.shapes.medium.bottomStart
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SizeSmall)
    ) {
        Spacer(modifier = Modifier.width(SizeMedium))
        Box(
            modifier = Modifier
                .weight(1f, false)
                .background(
                    color = color,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = SizeMedium,
                        bottomEnd = SizeMedium,
                        bottomStart = SizeMedium
                    ),
                )
                .padding(horizontal = SizeMedium, vertical = SizeSmall)
        ) {
            Text(
                text = message,
                color = textColor
            )
        }
        Spacer(modifier = Modifier.width(SizeLarge))
        Text(
            text = formattedTime,
            modifier = Modifier.align(Alignment.Bottom),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}