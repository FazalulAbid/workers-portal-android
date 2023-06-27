package com.fifty.workersportal.featureworker.presentation.workerprofile

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.LargeDisplayProfilePicture
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.SkyBlueColor
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureworker.presentation.component.ButtonBetweenLines
import com.fifty.workersportal.featureworker.presentation.component.Chip
import com.fifty.workersportal.featureworker.presentation.component.RatingAndRatingCount
import com.fifty.workersportal.featureworker.presentation.component.SampleWorkItem
import com.fifty.workersportal.featureworker.presentation.component.WorkerWageText
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import javax.security.auth.login.LoginException

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun WorkerProfileScreen(
    workerId: String? = null,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    isSampleWorkAdded: Boolean,
    isWorkerProfileUpdated: Boolean,
    imageLoader: ImageLoader,
    viewModel: WorkerProfileViewModel = hiltViewModel()
) {
    val showSampleWorkImage = remember { mutableStateOf(false) }
    val state = viewModel.state.value
    val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
    val sampleWorks =
        viewModel.sampleWorks.collectAsLazyPagingItems()
    val noSampleWorksLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_box_lottie)
    )
    val noSampleWorksLottieProgress by animateLottieCompositionAsState(
        composition = noSampleWorksLottieComposition, iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = true) {
        viewModel.getProfile(workerId)
    }

    LaunchedEffect(Unit) {
        if (isSampleWorkAdded) {
            viewModel.onEvent(WorkerProfileEvent.UpdateSampleWorks)
        }
        if (isWorkerProfileUpdated) {
            viewModel.onEvent(WorkerProfileEvent.UpdateWorkerProfileDetails)
        }
    }

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
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navActions = {
                if (state.isOwnProfile) {
                    IconButton(
                        onClick = {
                            onNavigate(Screen.RegisterAsWorkerScreen.route)
                        }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = stringResource(R.string.edit_worker_profile),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        )
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(3)
            ) {
                item(span = { GridItemSpan(3) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SizeMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(SizeMedium))
                        LargeDisplayProfilePicture(
                            painter = rememberImagePainter(
                                data = state.profile?.profilePicture,
                                imageLoader = imageLoader
                            )
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.offset(
                                x = if (state.profile?.isVerifiedWorker == true) {
                                    (SizeMedium / 2f)
                                } else 0.dp
                            )
                        ) {
                            Text(
                                modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                                text = "${state.profile?.firstName} ${state.profile?.lastName}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (state.profile?.isVerifiedWorker == true) {
                                Spacer(modifier = Modifier.width(SizeExtraSmall))
                                Icon(
                                    modifier = Modifier.size(SizeLarge),
                                    tint = SkyBlueColor,
                                    painter = painterResource(id = R.drawable.ic_verification),
                                    contentDescription = stringResource(R.string.verification_badge)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(SizeExtraSmall))
                        val primarySkill = state.profile?.categoryList?.find {
                            it.id == state.profile.primaryCategory
                        }
                        primarySkill?.skill?.let { primarySkillName ->
                            Text(
                                modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                                text = primarySkillName,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Normal
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(SizeSmall))
                        }
                        state.profile?.bio?.let { bio ->
                            Text(
                                modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                                text = bio,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Light
                                ),
                            )
                            Spacer(modifier = Modifier.height(SizeLarge))
                        }
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisAlignment = MainAxisAlignment.Center,
                            mainAxisSpacing = SizeMedium,
                            crossAxisSpacing = SizeMedium
                        ) {
                            state.profile?.categoryList?.forEach { workerCategory ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Chip(
                                        text = workerCategory.skill ?: ""
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(SizeLarge))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(SizeLarge))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingAndRatingCount(
                                modifier = Modifier.clickable {
                                    onNavigate(Screen.ReviewAndRatingScreen.route)
                                },
                                rating = "4.5",
                                ratingCount = 123
                            )
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
                        Spacer(modifier = Modifier.height(SizeLarge))
                    }
                }
                item(
                    span = { GridItemSpan(3) }) {
                    SecondaryHeader(
                        modifier = Modifier.padding(
                            vertical = SizeMedium,
                            horizontal = SizeMedium
                        ),
                        text = stringResource(R.string.x_s_works, state.profile?.firstName ?: ""),
                        style = MaterialTheme.typography.titleMedium,
                        moreOption = state.isOwnProfile,
                        moreOptionText = stringResource(id = R.string.post_your_work),
                        onMoreOptionClick = {
                            onNavigate(Screen.PostSampleWorkScreen.route)
                        }
                    )
                }
                if (sampleWorks.itemCount > 0) {
                    items(sampleWorks.itemCount) { index ->
                        sampleWorks[index]?.let {
                            SampleWorkItem(
                                sampleWork = it,
                                imageLoader = imageLoader,
                                onClick = {
                                    viewModel.onEvent(WorkerProfileEvent.ClickSampleWork(it))
                                    showSampleWorkImage.value = true
                                }
                            )
                        }
                    }
                } else {
                    item(span = { GridItemSpan(3) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(SizeMedium),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottieAnimation(
                                modifier = Modifier.size(ExtraExtraLargeProfilePictureHeight),
                                composition = noSampleWorksLottieComposition,
                                progress = noSampleWorksLottieProgress
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSampleWorkImage.value) {
        SampleWorkViewDialog(
            imageUrl = state.clickedSampleWork?.imageUrl ?: "",
            title = state.clickedSampleWork?.title ?: "",
            description = state.clickedSampleWork?.description ?: "",
            imageLoader = imageLoader,
            setShowDialog = {
                showSampleWorkImage.value = it
            }
        )
    }
}