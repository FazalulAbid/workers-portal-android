package com.fifty.fixitnow.featurelocation.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress

@Composable
fun SavedAddressItem(
    modifier: Modifier = Modifier,
    iconPainter: Painter = painterResource(id = R.drawable.ic_location_mark),
    iconTint: Color = MaterialTheme.colorScheme.primary,
    localAddress: LocalAddress,
    onMoreClick: () -> Unit,
    onShareClick: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = modifier.padding(SizeMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(SizeMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = localAddress.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SizeExtraSmall))
                Text(
                    text = "${appendComma(localAddress.place)}${appendComma(localAddress.subLocality)}${
                        appendComma(
                            localAddress.city
                        )
                    }${localAddress.state}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SizeSmall))
            }
            Spacer(modifier = Modifier.width(SizeMedium))
//            IconButton(onClick = {
//
//            }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_edit),
//                    contentDescription = stringResource(
//                        R.string.edit_address
//                    ),
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = SizeMedium))
    }
}

fun appendComma(input: String?): String {
    if (!input.isNullOrBlank()) {
        return "$input, "
    }
    return ""
}