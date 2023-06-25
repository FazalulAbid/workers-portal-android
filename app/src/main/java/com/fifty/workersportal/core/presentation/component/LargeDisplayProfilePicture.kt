package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.fifty.workersportal.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun LargeDisplayProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    isEditable: Boolean = false,
    size: Dp = ExtraExtraLargeProfilePictureHeight,
    onClickEdit: () -> Unit = {}
) {

    Box(
        modifier = if (!isEditable) {
            modifier
                .border(
                    width = LargeStrokeThickness,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(SizeSmall)
        } else modifier
    ) {
        Image(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .clickable { onClickEdit() },
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}