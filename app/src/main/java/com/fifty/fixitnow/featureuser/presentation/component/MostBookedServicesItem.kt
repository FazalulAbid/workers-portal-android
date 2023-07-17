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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.DarkGreenColor
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.bounceClick

@Composable
fun MostBookedServicesItem(
    modifier: Modifier = Modifier,
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
                painter = painterResource(id = R.drawable.plumber_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onFavouriteClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite_filled),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        Text(
            text = "Fazalul Abid",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
        Text(
            text = "Plumber",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(SizeMedium),
                painter = painterResource(id = R.drawable.ic_rating_star_filled),
                contentDescription = null,
                tint = DarkGreenColor
            )
            Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
            Text(
                text = "4.5 (123 Ratings)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}