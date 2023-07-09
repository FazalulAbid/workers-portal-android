package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.AddToFavouriteButton
import com.fifty.workersportal.core.presentation.ui.theme.DarkGreenColor
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureworker.domain.model.Worker
import java.lang.StringBuilder

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WorkerListItem(
    worker: Worker,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {}
) {
    Row(
        Modifier
            .clickable { onClick() }
            .padding(
                horizontal = SizeMedium,
                vertical = SizeSmall
            )
    ) {
        Image(
            modifier = Modifier
                .size(LargeProfilePictureHeight)
                .clip(MaterialTheme.shapes.small),
            painter = rememberImagePainter(
                data = worker.profileImageUrl,
                imageLoader = imageLoader
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        Column(
            modifier = Modifier
                .heightIn(min = LargeProfilePictureHeight)
                .weight(1f)
        ) {
            Text(
                text = "${worker.firstName} ${worker.lastName}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    painter = painterResource(id = R.drawable.ic_handshake_filled),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                Text(
                    text = worker.primaryCategoryName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = stringResource(R.string.rating_star),
                    tint = DarkGreenColor
                )
                Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                Text(
                    text = if (worker.ratingAverage == null || worker.ratingAverage <= 0) {
                        stringResource(R.string.no_ratings)
                    } else buildString {
                        append(worker.ratingAverage)
                        append(" (")
                        append(worker.ratingCount)
                        append(") ${stringResource(R.string.rating_star)}")
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    painter = painterResource(id = R.drawable.ic_location_mark),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                Text(
                    text = displayPlace(worker.localAddress)
                        ?: stringResource(R.string.no_place_found),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(SizeMedium))
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .heightIn(min = LargeProfilePictureHeight),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            AddToFavouriteButton(
                isFavourite = worker.isFavourite,
                onClick = onFavouriteClick
            )
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = SizeSmall, vertical = SizeExtraSmall),
                text = worker.primaryCategoryDailyWage.toString() + "/Day",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

fun displayPlace(address: LocalAddress?): String? {
    address?.let {
        val lists = arrayListOf<String>()
        if (!address.subLocality.isNullOrBlank()) {
            lists.add(address.subLocality)
        }
        if (!address.city.isNullOrBlank()) {
            lists.add(address.city)
        }
        if (!address.state.isNullOrBlank() && lists.size < 2) {
            lists.add(address.state)
        }
        if (!address.country.isNullOrBlank() && lists.size < 2) {
            lists.add(address.country)
        }
        return lists.joinToString(", ")
    } ?: return null
}