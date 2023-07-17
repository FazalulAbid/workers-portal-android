package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.DarkGreenColor
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.presentation.component.displayPlace

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ChosenWorkerCard(
    worker: Worker,
    chosenCategoryName: String,
    chosenWage: String,
    imageLoader: ImageLoader
) {
    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .padding(SizeMedium)
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
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
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
                    text = "$chosenCategoryName (₹$chosenWage)",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
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
    }
}