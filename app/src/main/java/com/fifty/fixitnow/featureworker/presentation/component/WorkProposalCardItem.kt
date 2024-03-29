package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.LottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.featurelocation.domain.model.toDisplayAddress
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalForWorker

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WorkProposalCardItem(
    workProposal: WorkProposalForWorker,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    onClick: () -> Unit = {},
) {
    Column(
        modifier
            .clickable { onClick() }
            .padding(
                horizontal = SizeMedium,
                vertical = SizeSmall
            )
    ) {
        Row {
            Image(
                modifier = Modifier
                    .size(LargeProfilePictureHeight)
                    .clip(MaterialTheme.shapes.small),
                painter = rememberImagePainter(
                    data = workProposal.profileImageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(SizeMedium))
            Column(
                modifier = Modifier
                    .heightIn(min = LargeProfilePictureHeight)
                    .weight(1f)
            ) {
                Text(
                    text = "${workProposal.firstName} ${workProposal.lastName}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(SizeMedium),
                        painter = painterResource(id = R.drawable.ic_handshake_filled),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(SizeExtraExtraSmall))
                    Text(
                        text = workProposal.categoryTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(SizeExtraExtraSmall))
                Text(
                    text = workProposal.proposedAddress.toDisplayAddress() ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(SizeMedium))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .heightIn(min = LargeProfilePictureHeight),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = workProposal.formattedProposedDate,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = workProposal.workType,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                }
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = SizeSmall, vertical = SizeExtraSmall),
                    text = workProposal.displayWage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        val builtString = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Medium
                )
            ) {
                append("Work Description: ")
            }
            append(workProposal.workDescription)
        }
        Text(
            text = builtString,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}