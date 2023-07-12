package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.featureworker.domain.model.SampleWork

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SampleWorkItem(
    modifier: Modifier = Modifier,
    sampleWork: SampleWork,
    imageLoader: ImageLoader,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable {
                onClick()
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = sampleWork.imageUrl,
                imageLoader = imageLoader,
            ),
            contentDescription = null
        )
    }
}