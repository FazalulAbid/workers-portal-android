package com.fifty.workersportal.featureworker.presentation.reviewandrating

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.component.ratingbar.RatingBar
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.presentation.component.RatingsDetailedCountBars
import com.fifty.workersportal.featureworker.presentation.component.ReviewItem
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewAndRatingScreen(
    onNavigate: (String) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ReviewAndRatingViewModel = hiltViewModel()
) {

    var showWriteReviewSheet by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    makeToast(event.uiText.asString(context), context)
                }

                UiEvent.ReviewAndRatingPosted -> {
                    showWriteReviewSheet = false
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                ReviewAndRatingError.EmptyField -> {
                    makeToast(R.string.review_filed_can_t_be_empty, context)
                }

                ReviewAndRatingError.RatingError -> {
                    makeToast(R.string.drag_through_rating_stars_to_select_your_rating, context)
                }
            }
        }
    }

    Column() {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.reviews_and_ratings),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SizeMedium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.ratings),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                    Spacer(modifier = Modifier.height(SizeSmall))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Text(
                        text = "4.0",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    var rating3 by remember { mutableStateOf(2.3f) }
                    RatingBar(
                        rating = rating3,
                        painterEmpty = painterResource(id = R.drawable.ic_rating_star),
                        painterFilled = painterResource(id = R.drawable.ic_rating_star_filled),
                        tintEmpty = Color(0xFFFF8D00),
                        tintFilled = Color(0xFFFF8D00),
                        animationEnabled = true,
                        gestureEnabled = false,
                        itemSize = SizeExtraLarge
                    ) {
                        rating3 = it
                    }
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Text(
                        text = "based on 32 reviews",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(SizeMedium))
                    RatingsDetailedCountBars(
                        excellentValue = 0.6f,
                        goodValue = 0.7f,
                        averageValue = 0.9f,
                        belowAverageValue = 0.8f,
                        poorValue = 0.5f,

                        )
                    Spacer(modifier = Modifier.height(SizeMedium))
                    HorizontalDivider()
                }
            }
            val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            items(numbers) {
                ReviewItem()
                if (numbers.last() != it) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = SizeMedium))
                }
            }
        }

        PrimaryButton(
            modifier = Modifier.padding(SizeMedium),
            text = stringResource(R.string.write_a_review)
        ) {
            showWriteReviewSheet = true
        }

        if (showWriteReviewSheet) {
            StandardBottomSheet(
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onDismiss = {
                    showWriteReviewSheet = false
                }
            ) {
                WriteAReviewScreenContent(
                    viewModel = viewModel,
                    onSubmitClick = {
                        viewModel.onEvent(ReviewAndRatingEvent.PostReviewAndRating)
                    }
                )
            }
        }
    }
}