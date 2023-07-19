package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun SecondaryHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    fontWeight: FontWeight = FontWeight.Medium,
    maxLines: Int = 1,
    moreOption: Boolean = false,
    moreOptionPainter: Painter = painterResource(id = R.drawable.ic_alt_arrow_forward),
    moreOptionText: String = "",
    onMoreOptionClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            style = style.copy(
                fontWeight = fontWeight,
                color = color
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        if (moreOption) {
            Row(
                modifier = Modifier.clickable { onMoreOptionClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = moreOptionText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(SizeSmall))
                Icon(
                    painter = moreOptionPainter,
                    contentDescription = stringResource(id = R.string.show_more),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}