package com.fifty.workersportal.featureworker.presentation.reviewandrating

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.StandardMultilineTextField
import com.fifty.workersportal.core.presentation.component.ratingbar.RatingBar
import com.fifty.workersportal.core.presentation.ui.theme.GoldColor
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WriteAReviewScreenContent(
    modifier: Modifier = Modifier,
    viewModel: ReviewAndRatingViewModel,
    onSubmitClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val reviewAndRatingLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.rating_and_review_lottie)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SizeMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(150.dp),
            composition = reviewAndRatingLottieComposition
        )
        Spacer(modifier = Modifier.height(SizeMedium))
        Text(
            text = stringResource(R.string.how_was_your_experience),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(SizeMedium))
        RatingBar(
            rating = viewModel.ratingState.value.toFloat(),
            tintEmpty = GoldColor,
            tintFilled = GoldColor,
            animationEnabled = true,
            itemSize = MediumProfilePictureHeight,
            onRatingChange = {
                viewModel.onEvent(ReviewAndRatingEvent.RatingValueChange(it))
            }
        )
        Spacer(modifier = Modifier.height(SizeMedium))
        StandardMultilineTextField(
            basicTextFieldModifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (3f * MediumButtonHeight), max = (5f * MediumButtonHeight))
                .padding(vertical = SizeMedium)
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            hint = stringResource(R.string.write_your_review_here),
            value = viewModel.reviewTextFieldState.value.text,
            onValueChange = {
                viewModel.onEvent(ReviewAndRatingEvent.ReviewChanged(it))
            },
            singleLine = false,
            maxLength = 1000,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onDone = {}
            )
        )
        PrimaryButton(
            modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
            text = stringResource(R.string.post_review)
        ) {
            onSubmitClick()
        }
    }
}