package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun RegisterPagerNavItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit = {}
) {
    val lineLength = animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Column(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(SizeSmall)
                .drawBehind {
                    if (lineLength.value > 0f) {
                        drawLine(
                            color = selectedColor,
                            start = Offset(
                                size.width / 2f - lineLength.value * 20.dp.toPx(),
                                size.height
                            ),
                            end = Offset(
                                size.width / 2f + lineLength.value * 20.dp.toPx(),
                                size.height
                            ),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
        ) {
            Text(
                modifier = Modifier.padding(vertical = SizeSmall),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                )
            )
        }
    }
}