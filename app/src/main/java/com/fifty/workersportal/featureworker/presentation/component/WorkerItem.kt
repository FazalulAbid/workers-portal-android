package com.fifty.workersportal.featureworker.presentation.component

import android.widget.Space
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.AddToFavouriteButton
import com.fifty.workersportal.core.presentation.component.RatingAndRatingCountHorizontal
import com.fifty.workersportal.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.core.presentation.util.bounceClick
import com.fifty.workersportal.featurelocation.domain.model.toDisplayAddress
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick {
                onClick()
            }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = SmallStrokeThickness,
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.outline
                )
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
                    contentDescription = null
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
                }
            }
            Spacer(modifier = Modifier.height(SizeMedium))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = MainAxisAlignment.Start,
                mainAxisSpacing = SizeSmall,
                crossAxisSpacing = SizeSmall
            ) {
                val workerCategories = listOf(
                    WorkerCategory(
                        "1",
                        "Plumber",
                        "Plumbing",
                        "",
                        500.0f,
                        600.0f,
                        "0.0",
                        "0.0"
                    ),
                    WorkerCategory(
                        "2",
                        "Cleaner",
                        "Cleaning",
                        "",
                        400.0f,
                        550.0f,
                        "0.0",
                        "0.0"
                    ),
                    WorkerCategory(
                        "3",
                        "Electrician",
                        "Electrical",
                        "",
                        600.0f,
                        700.0f,
                        "0.0",
                        "0.0"
                    ),
                    WorkerCategory(
                        "4",
                        "Carpenter",
                        "Carpentry",
                        "",
                        450.0f,
                        550.0f,
                        "0.0",
                        "0.0"
                    ),
                    WorkerCategory(
                        "5",
                        "Gardener",
                        "Gardening",
                        "",
                        550.0f,
                        650.0f,
                        "0.0",
                        "0.0"
                    ),
                )
                workerCategories.forEach {
                    Chip(
                        text = it.title ?: "",
                        selected = it.id == "3",
                        textStyle = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (it.id == "3") {
                                FontWeight.Medium
                            } else FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}