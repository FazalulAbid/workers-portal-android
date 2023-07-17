package com.fifty.fixitnow.featureuser.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.bounceClick
import com.fifty.fixitnow.featureworker.domain.model.Category

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SuggestedCategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    imageSize: Dp = LargeProfilePictureHeight,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier.padding(SizeExtraSmall)) {
        Column(
            modifier = modifier
                .width(ExtraLargeProfilePictureHeight)
                .bounceClick {
                    onClick()
                }
                .padding(SizeSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(imageSize),
                painter = rememberImagePainter(
                    data = "https://cdn-icons-png.flaticon.com/512/1670/1670444.png",
                    imageLoader = imageLoader
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Text(
                text = category.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}