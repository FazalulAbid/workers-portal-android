package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.bounceClick

@Composable
fun LargeDisplayProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    size: Dp = ExtraExtraLargeProfilePictureHeight,
    onClickEdit: (() -> Unit)? = null
) {
    Box(
        modifier = if (onClickEdit == null) {
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
                .run {
                    if (onClickEdit != null) {
                        bounceClick {
                            onClickEdit()
                        }
                    } else this
                }
                .size(size)
                .clip(CircleShape),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}