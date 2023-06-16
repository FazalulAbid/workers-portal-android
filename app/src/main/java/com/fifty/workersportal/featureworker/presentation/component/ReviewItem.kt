package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.ratingbar.RatingBar
import com.fifty.workersportal.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SizeMedium)
    ) {
        Spacer(modifier = Modifier.height(SizeMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(ExtraSmallProfilePictureHeight)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.plumber_profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(SizeMedium))
                Column(verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = "Fazalul Abid",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingBar(
                            rating = 5.0f,
                            tintEmpty = Color(0xFFFF8D00),
                            tintFilled = Color(0xFFFF8D00),
                            animationEnabled = true,
                            gestureEnabled = false,
                            itemSize = SizeLarge
                        )
                        Spacer(modifier = Modifier.width(SizeSmall))
                        Text(
                            text = "5.0",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
            Text(
                text = "2 days ago",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Right
                )
            )
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        Text(
            text = "He provided an exceptional service. They were prompt, professional, and efficient in resolving my plumbing issue. The plumber arrived on time, assessed the problem quickly, and provided a clear explanation of the necessary repairs. Their workmanship was top-notch, and they ensured that everything was functioning perfectly before leaving.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(SizeMedium))
    }
}