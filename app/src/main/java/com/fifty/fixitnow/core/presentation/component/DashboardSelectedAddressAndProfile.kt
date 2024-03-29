package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SmallProfilePictureHeight
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featurelocation.domain.model.toDisplayAddress

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DashboardSelectedAddressAndProfile(
    profileImageUrl: String,
    localAddress: LocalAddress?,
    imageLoader: ImageLoader,
    onProfileClick: () -> Unit = {},
    onLocationClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) {
                    onLocationClick()
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeLarge),
                    painter = painterResource(id = R.drawable.ic_near_me),
                    contentDescription = stringResource(R.string.selected_address),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(SizeExtraSmall))
                Text(
                    text = localAddress?.title ?: stringResource(id = R.string.not_available),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_expand_more),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = localAddress.toDisplayAddress()
                    ?: stringResource(id = R.string.tap_here_to_select_an_address),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.width(SizeMedium))
        Image(
            modifier = Modifier
                .size(SmallProfilePictureHeight)
                .clip(CircleShape)
                .clickable { onProfileClick() },
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = profileImageUrl,
                imageLoader = imageLoader
            ),
            contentDescription = stringResource(R.string.profile_picture)
        )
    }
}