package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.fifty.workersportal.core.presentation.ui.theme.GoldColor
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.LightGrayColor
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun SelectableRatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    icon: Painter,
    iconSize: Dp = LargeButtonHeight,
    iconSpacedBy: Dp = SizeSmall,
    maxRating: Int = 5,
    selectedIconColor: Color = GoldColor,
    unselectedIconColor: Color = LightGrayColor,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(iconSpacedBy)
    ) {
        for (index in 1..maxRating) {
            val starColor = if (index <= rating) selectedIconColor else unselectedIconColor
            IconButton(onClick = { onRatingChanged(index) }) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = starColor,
                    modifier = Modifier
                        .size(iconSize)
                )
            }
        }
    }
}