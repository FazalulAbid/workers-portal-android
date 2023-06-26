package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.ratingbar.RatingBar
import com.fifty.workersportal.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    reviewAndRating: ReviewAndRating
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SizeMedium)
    ) {
        Spacer(modifier = Modifier.height(SizeMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(ExtraSmallProfilePictureHeight)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.plumber_profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(SizeMedium))
                Column(verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = reviewAndRating.ratingUsername,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingBar(
                            rating = reviewAndRating.rating,
                            tintEmpty = Color(0xFFFF8D00),
                            tintFilled = Color(0xFFFF8D00),
                            animationEnabled = true,
                            gestureEnabled = false,
                            itemSize = SizeLarge
                        )
                        Spacer(modifier = Modifier.width(SizeSmall))
                        Text(
                            text = reviewAndRating.rating.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
            Text(
                text = "2 days ago (Pending)",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Right
                )
            )
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        Text(
            text = reviewAndRating.review,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(SizeMedium))
    }
}