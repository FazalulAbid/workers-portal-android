package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.DarkGreenColor
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun RatingAndRatingCountHorizontal(
    modifier: Modifier = Modifier,
    averageRating: Float?,
    ratingCount: Int
) {
    if (averageRating != null && ratingCount > 0) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier
                    .background(DarkGreenColor, MaterialTheme.shapes.small)
                    .padding(
                        vertical = SizeExtraExtraSmall,
                        horizontal = SizeExtraSmall
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    painter = painterResource(id = R.drawable.ic_rating_star_filled),
                    contentDescription = stringResource(id = R.string.rating_star),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                Text(
                    text = averageRating.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.width(SizeSmall))
            Text(
                text = "(${ratingCount}) Ratings",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        Row {
            Icon(
                modifier = Modifier.size(SizeMedium),
                painter = painterResource(id = R.drawable.ic_rating_star_filled),
                contentDescription = "",
                tint = DarkGreenColor
            )
            Spacer(modifier = Modifier.width(SizeExtraSmall))
            Text(
                text = stringResource(id = R.string.no_ratings),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}