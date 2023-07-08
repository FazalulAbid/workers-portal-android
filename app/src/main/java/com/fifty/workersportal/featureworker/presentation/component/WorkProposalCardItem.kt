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
import com.airbnb.lottie.LottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.AddToFavouriteButton
import com.fifty.workersportal.core.presentation.ui.theme.DarkGreenColor
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun WorkProposalCardItem(
    onClick: () -> Unit = {},
    isFavourite: Boolean,
    lottieComposition: LottieComposition?,
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
            painter = painterResource(id = R.drawable.plumber_profile),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        Column(
            modifier = Modifier
                .heightIn(min = LargeProfilePictureHeight)
                .weight(1f)
        ) {
            Text(
                text = "Fazalul Abid",
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
                    contentDescription = stringResource(R.string.rating_star),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                Text(
                    text = "Programmer",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
            Text(
                text = "Mumbai, Maharashtra",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(SizeMedium))
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .heightIn(min = LargeProfilePictureHeight),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Wed, 10th Jun 2022",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Full Day",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

            }
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = SizeSmall, vertical = SizeExtraSmall),
                text = "$99.00/day",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}