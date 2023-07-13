package com.fifty.workersportal.featureworker.presentation.component

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.bounceClick
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WorkerCategoryChip(
    modifier: Modifier = Modifier,
    workerCategory: WorkerCategory,
    imageLoader: ImageLoader,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .run {
                if (onClick != null) {
                    bounceClick { onClick() }
                } else this
            }
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(SizeMedium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(ExtraSmallProfilePictureHeight)
                    .clip(MaterialTheme.shapes.medium),
                painter = rememberImagePainter(
                    data = workerCategory.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(SizeSmall))
            Text(
                text = workerCategory.skill ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "₹${workerCategory.dailyWage}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Per Day",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Spacer(modifier = Modifier.width(SizeMedium))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${workerCategory.hourlyWage}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Per Half Day",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}