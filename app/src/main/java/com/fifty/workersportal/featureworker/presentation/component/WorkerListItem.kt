package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.DarkGreenColor
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun WorkerListItem(

) {
    Row(
        Modifier
            .clickable { }
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
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
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
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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
                            text = "4.5 (1242 Ratings)",
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(SizeMedium))
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.primary)
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
}