package com.fifty.fixitnow.featureworker.presentation.workerprofile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.AddToFavouriteButton
import com.fifty.fixitnow.core.presentation.component.EmptyListLottie
import com.fifty.fixitnow.core.presentation.component.LargeDisplayProfilePicture
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SkyBlueColor
import com.fifty.fixitnow.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.fixitnow.core.presentation.util.ToastExt
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureworker.presentation.component.LocalAddressDisplayLarge
import com.fifty.fixitnow.featureworker.presentation.component.RatingAndRatingCountVertical
import com.fifty.fixitnow.featureworker.presentation.component.SampleWorkItem
import com.fifty.fixitnow.featureworker.presentation.component.WorkerCategoryChip
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalEvent
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.maxkeppeker.sheets.core.views.Grid

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun WorkerProfileScreen(
    workerId: String? = null,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    isSampleWorkAdded: Boolean,
    isWorkerProfileUpdated: Boolean,
    imageLoader: ImageLoader,
    workerProfileViewModel: WorkerProfileViewModel = hiltViewModel(),
    workProposalViewModel: WorkProposalViewModel
) {
    val context = LocalContext.current
    val showSampleWorkImage = remember { mutableStateOf(false) }
    val state = workerProfileViewModel.state.value
    val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
    val sampleWorks =
        workerProfileViewModel.sampleWorks.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        workerProfileViewModel.getProfile(workerId)
    }

    LaunchedEffect(Unit) {
        if (isSampleWorkAdded) {
            workerProfileViewModel.onEvent(WorkerProfileEvent.UpdateSampleWorks)
        }
        if (isWorkerProfileUpdated) {
            workerProfileViewModel.onEvent(WorkerProfileEvent.UpdateWorkerProfileDetails)
        }
    }

    var isFavouriteTemp = remember { mutableStateOf(false) }

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
                } else {
                    AddToFavouriteButton(
                        isFavourite = state.worker?.isFavourite == true,
                        onClick = {
                            workerProfileViewModel.onEvent(WorkerProfileEvent.ToggleFavourite)
                        }
                    )
                }
            }
        )
        if (!state.isLoading) {
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
                                    data = state.worker?.profileImageUrl,
                                    imageLoader = imageLoader
                                )
                            )
                            Spacer(modifier = Modifier.height(SizeMedium))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.offset(
                                    x = if (state.worker?.isVerified == true) {
                                        (SizeMedium / 2f)
                                    } else 0.dp
                                )
                            ) {
                                Text(
                                    modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                                    text = "${state.worker?.firstName} ${state.worker?.lastName}",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (state.worker?.isVerified == true) {
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
                            val primarySkill = state.worker?.categoryList?.find {
                                it.id == state.worker.primaryCategoryId
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
                            state.worker?.bio?.let { bio ->
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
                            if (state.worker?.openToWork == false) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color(0x1AFF0000),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .padding(vertical = SizeSmall, horizontal = SizeMedium),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(SizeMedium),
                                        painter = painterResource(id = R.drawable.ic_circle_xmark),
                                        contentDescription = null,
                                        tint = Color(0xFFFF0000)
                                    )
                                    Spacer(modifier = Modifier.width(SizeSmall))
                                    Text(
                                        text = "${state.worker.firstName} is not currently open to work",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFFFF0000)
                                        )
                                    )
                                }
                            }
                        }
                    }
                    item(span = { GridItemSpan(3) }) {
                        state.worker?.categoryList?.let { workerCategories ->
                            Grid(
                                modifier = Modifier
                                    .padding(SizeMedium),
                                items = workerCategories,
                                columns = 2,
                                rowSpacing = SizeMedium,
                                columnSpacing = SizeMedium
                            ) { workerCategory ->
                                WorkerCategoryChip(
                                    modifier = Modifier.fillMaxWidth(),
                                    workerCategory = workerCategory,
                                    imageLoader = imageLoader,
                                    onClick = if (!state.isOwnProfile) {
                                        {
                                            if (state.worker.openToWork) {
                                                workProposalViewModel.onEvent(
                                                    WorkProposalEvent.SelectWorker(
                                                        state.worker
                                                    )
                                                )
                                                workProposalViewModel.onEvent(
                                                    WorkProposalEvent.SelectCategory(
                                                        workerCategory
                                                    )
                                                )
                                                onNavigate(Screen.WorkProposalScreen.route)
                                            } else {
                                                ToastExt.makeText(
                                                    context = context,
                                                    message = "${state.worker.firstName} ${state.worker.lastName} is not currently accepting works",
                                                )
                                            }
                                        }
                                    } else null
                                )
                            }
                        }
                    }
                    item(span = { GridItemSpan(3) }) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .padding(
                                        start = SizeMedium,
                                        end = SizeMedium,
                                        top = SizeLarge,
                                        bottom = SizeMedium
                                    ),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RatingAndRatingCountVertical(
                                    modifier = Modifier.clickable {
                                        onNavigate(Screen.ReviewAndRatingScreen.route + "?userId=${state.worker?.workerId}")
                                    },
                                    rating = state.worker?.ratingAverage.toString(),
                                    ratingCount = state.worker?.ratingCount ?: 0
                                )
                                Spacer(modifier = Modifier.width(SizeMedium))
                                Divider(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(SmallStrokeThickness),
                                    color = MaterialTheme.colorScheme.outline
                                )
                                Spacer(modifier = Modifier.width(SizeMedium))
                                LocalAddressDisplayLarge(
                                    localAddress = state.worker?.localAddress
                                )
                            }
                        }
                    }

                    item(span = { GridItemSpan(3) }) {
                        SecondaryHeader(
                            modifier = Modifier.padding(SizeMedium),
                            text = stringResource(
                                R.string.x_s_works,
                                state.worker?.firstName ?: ""
                            ),
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
                                        workerProfileViewModel.onEvent(
                                            WorkerProfileEvent.ClickSampleWork(
                                                it
                                            )
                                        )
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
                                EmptyListLottie()
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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