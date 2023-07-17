package com.fifty.fixitnow.featureauth.presentation.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.MediumStrokeThickness
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CountryPickerField(
    modifier: Modifier = Modifier,
    flagImageUrl: String,
    imageLoader: ImageLoader,
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
            painter = rememberImagePainter(
                data = flagImageUrl,
                imageLoader = imageLoader,
                builder = {
                    size(100)
                }
            ),
            contentDescription = stringResource(R.string.selected_country_flag),
            contentScale = ContentScale.Crop
        )
    }
}