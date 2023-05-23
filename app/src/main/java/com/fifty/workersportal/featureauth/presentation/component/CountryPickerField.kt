package com.fifty.workersportal.featureauth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.StrokeThickness

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CountryPickerField(
    modifier: Modifier = Modifier,
    flagImageUrl: String,
    onCountryClick: () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                StrokeThickness,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(SizeSmall)
            )
            .padding(horizontal = SizeMedium)
            .clickable {
                onCountryClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(SizeLarge)
                .clip(RoundedCornerShape(SizeExtraSmall)),
            painter = rememberImagePainter(
                data = flagImageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = stringResource(R.string.selected_country_flag)
        )
    }
}