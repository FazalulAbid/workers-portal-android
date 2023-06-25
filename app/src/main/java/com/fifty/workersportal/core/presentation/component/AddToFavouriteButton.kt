package com.fifty.workersportal.core.presentation.component

import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.LottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.RedColor

@Composable
fun AddToFavouriteButton(
    modifier: Modifier = Modifier,
    isFavourite: Boolean,
    lottieComposition: LottieComposition?,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        if (isFavourite) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_filled),
                contentDescription = stringResource(id = R.string.add_to_favourites),
                tint = RedColor
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.add_to_favourites),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}