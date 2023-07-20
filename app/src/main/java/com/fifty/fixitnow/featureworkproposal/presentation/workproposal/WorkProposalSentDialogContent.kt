package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun WorkProposalSentDialogContent(
    title: String,
    buttonText: String,
    onButtonClick: () -> Unit = {}
) {
    val doneLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.done_lottie)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SizeLarge)
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier.padding(SizeMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(140.dp),
                composition = doneLottieComposition
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            SecondaryHeader(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Column {
                WorkProposalSentDialogDetailItem(
                    headText = "Date",
                    detailText = "10th June 2022, Monday"
                )
                HorizontalDivider(
                    thickness = LargeStrokeThickness,
                    color = MaterialTheme.colorScheme.background
                )
                WorkProposalSentDialogDetailItem(
                    headText = "Date",
                    detailText = "10th June 2022, Monday"
                )
                HorizontalDivider(
                    thickness = LargeStrokeThickness,
                    color = MaterialTheme.colorScheme.background
                )
                WorkProposalSentDialogDetailItem(
                    headText = "Date",
                    detailText = "10th June 2022, Monday"
                )
            }
            Spacer(modifier = Modifier.height(SizeMedium))
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = buttonText, onClick = onButtonClick
            )
        }
    }
}

@Composable
fun WorkProposalSentDialogDetailItem(
    modifier: Modifier = Modifier,
    headText: String,
    detailText: String
) {
    Row(
        modifier.padding(SizeMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        Text(
            text = detailText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}