package com.fifty.workersportal.featureauth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.MediumStrokeThickness
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge

@Composable
fun CountryPickerField(
    modifier: Modifier = Modifier,
    flagImageUrl: String,
    onCountryClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(SizeSmall))
            .clickable {
                onCountryClick()
            }
            .border(
                MediumStrokeThickness,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(SizeSmall)
            )
            .padding(horizontal = SizeMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(24.dp)
                .width(36.dp)
                .clip(RoundedCornerShape(SizeExtraSmall)),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = flagImageUrl).apply(block = fun ImageRequest.Builder.() {
                    size(100)
                    crossfade(true)
                }).build()
            ),
            contentDescription = stringResource(R.string.selected_country_flag),
            contentScale = ContentScale.Crop
        )
    }
}