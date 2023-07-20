package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SuccessDialogBoxWidth
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal

@Composable
fun WorkProposalSentDialogContent(
    title: String,
    worker: Worker,
    workProposal: WorkProposal,
    buttonText: String,
    onButtonClick: () -> Unit = {}
) {
    val doneLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.done_lottie)
    )
    Box(
        modifier = Modifier
            .width(SuccessDialogBoxWidth)
            .background(
                MaterialTheme.colorScheme.background,
                MaterialTheme.shapes.large
            )
            .padding(SizeLarge)

    ) {
        Column(
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
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            val styledText = buildAnnotatedString {
                append("You have sent a proposal to ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    append("${worker.firstName} ${worker.lastName}")
                }
                append(" on ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    append(workProposal.formattedProposedDate)
                    append(" ")
                    append(
                        if (workProposal.isFullDay) {
                            "(Full Day)"
                        } else {
                            if (workProposal.isBeforeNoon) {
                                "(Before Noon)"
                            } else "(After Noon)"
                        }
                    )
                }
            }
            Text(
                text = styledText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(SizeLarge))
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = buttonText, onClick = onButtonClick
            )
        }
    }
}