package com.fifty.fixitnow.featureuser.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.AddToFavouriteButton
import com.fifty.fixitnow.core.presentation.component.RatingAndRatingCountHorizontal
import com.fifty.fixitnow.core.presentation.ui.theme.DarkGreenColor
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.bounceClick
import com.fifty.fixitnow.featureworker.domain.model.Worker

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MostBookedServicesItem(
    modifier: Modifier = Modifier,
    worker: Worker,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .bounceClick {
                onClick()
            }
            .padding(SizeSmall)
            .width(ExtraExtraLargeProfilePictureHeight)
    ) {
        Box(
            modifier = Modifier
                .width(ExtraExtraLargeProfilePictureHeight)
                .height(ExtraExtraLargeProfilePictureHeight)
                .clip(MaterialTheme.shapes.medium),
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(
                    data = worker.profileImageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
//            Box(
//                contentAlignment = Alignment.Center
//            ) {
//                AddToFavouriteButton(
//                    isFavourite = worker.isFavourite,
//                    unselectedColor = MaterialTheme.colorScheme.background,
//                    selectedColor = Color.Red,
//                    onClick = {
//                        onFavouriteClick()
//                    }
//                )
//            }
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        Text(
            text = "${worker.firstName} ${worker.lastName}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
        Text(
            text = worker.categoryList.find { it.id == worker.primaryCategoryId }?.title ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
        RatingAndRatingCountHorizontal(
            averageRating = worker.ratingAverage,
            ratingCount = worker.ratingCount
        )
    }
}