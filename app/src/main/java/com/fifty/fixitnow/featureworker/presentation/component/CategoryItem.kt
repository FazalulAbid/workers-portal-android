package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SmallProfilePictureHeight
import com.fifty.fixitnow.core.presentation.util.bounceClick
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category? = null,
    workerCategory: WorkerCategory? = null,
    imageSize: Dp = SmallProfilePictureHeight,
    imageLoader: ImageLoader,
    onClick: (() -> Unit)? = {}
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
            .padding(SizeSmall)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(imageSize)
                    .clip(MaterialTheme.shapes.small),
                painter = rememberImagePainter(
                    data = if (category != null) category.imageUrl else workerCategory?.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(SizeSmall))
            Column {
                Text(
                    text = category?.title ?: (workerCategory?.title ?: ""),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = category?.skill ?: (workerCategory?.skill ?: ""),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                val dailyWageText =
                    if (category != null) "${category.dailyMinWage}" else workerCategory?.dailyWage
                Text(
                    text = dailyWageText ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Daily",
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
                val halfDayWage =
                    if (category != null) "${category.hourlyMinWage}" else workerCategory?.hourlyWage
                Text(
                    text = halfDayWage ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Half Day",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}