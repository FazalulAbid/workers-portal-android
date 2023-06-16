package com.fifty.workersportal.featureworker.presentation.workerprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.ratingbar.RatingBar
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.presentation.component.RatingsDetailedCountBars
import com.fifty.workersportal.featureworker.presentation.component.ReviewItem

@Composable
fun RatingSheetContent(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SizeMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.reviews),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                    )
                )
                Spacer(modifier = Modifier.height(SizeSmall))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(SizeSmall))
                Text(
                    text = "4.0",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                var rating3 by remember { mutableStateOf(2.3f) }
                RatingBar(
                    rating = rating3,
                    painterEmpty = painterResource(id = R.drawable.ic_rating_star),
                    painterFilled = painterResource(id = R.drawable.ic_rating_star_filled),
                    tintEmpty = Color(0xFFFF8D00),
                    tintFilled = Color(0xFFFF8D00),
                    animationEnabled = true,
                    gestureEnabled = false,
                    itemSize = SizeExtraLarge
                ) {
                    rating3 = it
                }
                Spacer(modifier = Modifier.height(SizeSmall))
                Text(
                    text = "based on 32 reviews",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                RatingsDetailedCountBars(
                    excellentValue = 0.6f,
                    goodValue = 0.7f,
                    averageValue = 0.9f,
                    belowAverageValue = 0.8f,
                    poorValue = 0.5f,

                    )
                Spacer(modifier = Modifier.height(SizeMedium))
                HorizontalDivider()
            }
        }
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        items(numbers) {
            ReviewItem()
            if (numbers.last() != it) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = SizeMedium))
            }
        }
    }
}