package com.fifty.fixitnow.featureuser.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.MediumProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.bounceClick
import com.fifty.fixitnow.featureworker.domain.model.Category

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SuggestedCategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    imageSize: Dp = MediumProfilePictureHeight,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .bounceClick {
                onClick()
            }
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            )
            .padding(start = SizeSmall, top = SizeSmall, bottom = SizeSmall, end = SizeMedium)
            .widthIn(max = 160.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .size(imageSize),
            painter = rememberImagePainter(
                data = category.imageUrl,
                imageLoader = imageLoader
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(SizeSmall))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = category.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = category.skill,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}