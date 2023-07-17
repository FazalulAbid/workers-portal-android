package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fifty.fixitnow.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SmallButtonHeight
import com.fifty.fixitnow.core.presentation.util.bounceClick

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    height: Dp = LargeButtonHeight,
    progressBarSize: Dp = SmallButtonHeight,
    isLoading: Boolean = false,
    text: String = "",
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .bounceClick {},
        shape = RoundedCornerShape(SizeSmall),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        enabled = !isLoading,
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier
                        .size(progressBarSize),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}