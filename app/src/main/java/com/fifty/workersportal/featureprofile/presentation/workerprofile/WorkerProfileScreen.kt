package com.fifty.workersportal.featureprofile.presentation.workerprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.ExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.SkyBlueColor
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.featureprofile.presentation.component.ButtonBetweenLines
import com.fifty.workersportal.featureprofile.presentation.component.RatingAndRatingCount
import com.fifty.workersportal.featureprofile.presentation.component.WorkerWageText

@Composable
fun WorkerProfileScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.worker_profile),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            Modifier.padding(SizeMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.heightIn(SizeMedium))
            Image(
                painter = painterResource(id = R.drawable.plumber_profile),
                contentDescription = "Plumber description",
                Modifier
                    .size(ExtraLargeProfilePictureHeight)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.offset(x = (SizeMedium / 2f))
            ) {
                Text(
                    modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                    text = "Fazalul Abid",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(SizeExtraSmall))
                Icon(
                    modifier = Modifier.size(SizeMedium),
                    tint = SkyBlueColor,
                    painter = painterResource(id = R.drawable.ic_verification),
                    contentDescription = stringResource(R.string.verification_badge)
                )
            }
            Spacer(modifier = Modifier.height(SizeExtraSmall))
            Text(
                modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                text = "Plumber",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Text(
                modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                text = "A plumber installs, repairs, and maintains plumbing systems, fixtures, and pipes to ensure proper water flow and drainage.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                ),
            )
            Spacer(modifier = Modifier.height(SizeLarge))
            ButtonBetweenLines(text = stringResource(R.string.hire_now))
            Spacer(modifier = Modifier.height(SizeLarge))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingAndRatingCount(rating = "4.5", ratingCount = 123)
                Divider(
                    modifier = Modifier
                        .height(50.dp)
                        .width(SmallStrokeThickness),
                    color = MaterialTheme.colorScheme.outline
                )
                WorkerWageText(wage = 99.0f, isHalfDay = false)
                Divider(
                    modifier = Modifier
                        .height(50.dp)
                        .width(SmallStrokeThickness),
                    color = MaterialTheme.colorScheme.outline
                )
                WorkerWageText(wage = 59.0f, isHalfDay = true)
            }
            Spacer(modifier = Modifier.height(SizeMedium))
        }
    }
}