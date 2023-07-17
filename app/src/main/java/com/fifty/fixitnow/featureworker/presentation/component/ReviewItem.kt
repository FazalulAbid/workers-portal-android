package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.core.presentation.component.ratingbar.RatingBar
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.featureworker.domain.model.ReviewAndRating

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
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
                    painter = rememberImagePainter(
                        data = reviewAndRating.profileImageUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(SizeMedium))
                Column(verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = "${reviewAndRating.firstName} ${reviewAndRating.lastName}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
            Spacer(modifier = Modifier.width(SizeMedium))
            Text(
                text = reviewAndRating.formattedTime,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Right
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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