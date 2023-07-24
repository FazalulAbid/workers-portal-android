package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.border
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.fixitnow.core.presentation.ui.theme.RedColor
import com.fifty.fixitnow.core.presentation.ui.theme.SmallStrokeThickness

@Composable
fun AddToFavouriteButton(
    modifier: Modifier = Modifier,
    isFavourite: Boolean,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedColor: Color = RedColor,
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
                tint = selectedColor
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.add_to_favourites),
                tint = unselectedColor
            )
        }
    }
}