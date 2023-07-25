package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SuccessDialogBoxHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SuccessDialogBoxWidth

@Composable
fun PrimarySuccessDialogContent(
    title: String,
    description: String,
    buttonText: String,
    lottie: LottieComposition?,
    isCancelable: Boolean = false,
    cancelButtonText: String = "",
    onCancelClick: () -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    val lottieProgress by animateLottieCompositionAsState(
        composition = lottie, iterations = LottieConstants.IterateForever
    )
    Column(
        Modifier
            .background(
                MaterialTheme.colorScheme.background,
                MaterialTheme.shapes.large
            )
            .padding(SizeMedium)
            .height(SuccessDialogBoxHeight)
            .width(SuccessDialogBoxWidth),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .padding(bottom = SizeLarge)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(140.dp),
                composition = lottie,
                progress = lottieProgress
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            SecondaryHeader(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Text(
                text = description, color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                ),
            )
        }
        PrimaryButton(text = buttonText, onClick = onButtonClick)
        if (isCancelable) {
            Spacer(modifier = Modifier.height(SizeSmall))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelClick
            ) {
                Text(
                    text = cancelButtonText,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}