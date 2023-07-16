package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun RatingsDetailedCountBars(
    modifier: Modifier = Modifier,
    excellentValue: Float = 0f,
    goodValue: Float = 0f,
    averageValue: Float = 0f,
    belowAverageValue: Float = 0f,
    poorValue: Float = 0f,
) {
    val green = Color(0xFF90C8AC)
    val lightGreen = Color(0xFFC4DFAA)
    val yellow = Color(0xFFFFE9AE)
    val orange = Color(0xFFFEBE8C)
    val red = Color(0xFFE97777)

    Column(
        modifier = modifier
    ) {
        RatingsDetailedItem(
            progressColor = green,
            progressValue = excellentValue,
            text = stringResource(R.string.excellent)
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        RatingsDetailedItem(
            progressColor = lightGreen,
            progressValue = goodValue,
            text = stringResource(R.string.good)
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        RatingsDetailedItem(
            progressColor = yellow,
            progressValue = averageValue,
            text = stringResource(R.string.average)
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        RatingsDetailedItem(
            progressColor = orange,
            progressValue = belowAverageValue,
            text = stringResource(R.string.below_average)
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        RatingsDetailedItem(
            progressColor = red,
            progressValue = poorValue,
            text = stringResource(R.string.poor)
        )
        Spacer(modifier = Modifier.height(SizeSmall))
    }
}

@Composable
fun RatingsDetailedItem(
    progressValue: Float = 0f,
    progressColor: Color,
    text: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        LinearProgressIndicator(
            progress = if (progressValue.isNaN()) 0f else progressValue,
            modifier = Modifier
                .weight(7f)
                .height(SizeSmall),
            color = progressColor,
            trackColor = MaterialTheme.colorScheme.surface
        )
    }
}