package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
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
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.component.AddToFavouriteButton
import com.fifty.fixitnow.core.presentation.component.RatingAndRatingCountHorizontal
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.fixitnow.core.presentation.util.bounceClick
import com.fifty.fixitnow.featurelocation.domain.model.toDisplayAddress
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WorkerItem(
    modifier: Modifier = Modifier,
    worker: Worker,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick { onClick() }
            .border(
                width = SmallStrokeThickness,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
    ) {
        Column(
            Modifier
                .padding(SizeMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .size(ExtraLargeProfilePictureHeight)
                        .clip(MaterialTheme.shapes.medium),
                    painter = rememberImagePainter(
                        data = worker.profileImageUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(SizeMedium))
                Column {
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${worker.firstName} ${worker.lastName}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(SizeSmall))
                            RatingAndRatingCountHorizontal(
                                averageRating = worker.ratingAverage,
                                ratingCount = worker.ratingCount
                            )
                        }
                        Spacer(modifier = Modifier.width(SizeMedium))
                        AddToFavouriteButton(
                            isFavourite = worker.isFavourite,
                            onClick = {
                                onFavouriteClick()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Row {
                        Icon(
                            modifier = Modifier.size(SizeMedium),
                            painter = painterResource(id = R.drawable.ic_location_mark),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(SizeExtraSmall))
                        Text(
                            text = worker.localAddress.toDisplayAddress() ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Row {
                        Icon(
                            modifier = Modifier.size(SizeMedium),
                            painter = painterResource(id = R.drawable.ic_distance),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(SizeExtraSmall))
                        Text(
                            text = "${worker.distance} from ${Session.selectedAddress.value?.title}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(SizeMedium))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = MainAxisAlignment.Start,
                mainAxisSpacing = SizeSmall,
                crossAxisSpacing = SizeSmall
            ) {
                worker.categoryList.forEach {
                    Chip(
                        text = it.title ?: "",
                        selected = it.id == worker.primaryCategoryId,
                        textStyle = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (it.id == worker.primaryCategoryId) {
                                FontWeight.Medium
                            } else FontWeight.Normal
                        )
                    )
                }
            }
        }
        if (!worker.openToWork) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x1AFF0000))
                    .padding(vertical = SizeSmall, horizontal = SizeMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    painter = painterResource(id = R.drawable.ic_circle_xmark),
                    contentDescription = null,
                    tint = Color(0xFFFF0000)
                )
                Spacer(modifier = Modifier.width(SizeSmall))
                Text(
                    text = "${worker.firstName} is not currently open to work",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF0000)
                    )
                )
            }
        }
    }
}