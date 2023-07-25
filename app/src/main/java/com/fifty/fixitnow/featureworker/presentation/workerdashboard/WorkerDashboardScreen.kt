package com.fifty.fixitnow.featureworker.presentation.workerdashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.component.DashboardSelectedAddressAndProfile
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.PrimaryHeader
import com.fifty.fixitnow.core.presentation.component.PrimarySuccessDialogContent
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.util.OnLifecycleEvent
import com.fifty.fixitnow.core.presentation.util.ToastExt
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.presentation.util.dp
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureworker.presentation.component.OpenToWorkSwitch
import com.fifty.fixitnow.featureworker.presentation.component.WorkProposalCardActionRow
import com.fifty.fixitnow.featureworker.presentation.component.WorkProposalDraggableCard
import kotlinx.coroutines.flow.collectLatest

const val ACTION_ITEM_SIZE = 56
const val CARD_HEIGHT = 56
const val CARD_OFFSET = 144f // we have 2 icons in a row, so that's 56 * 2 + SizeMedium

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkerDashboardScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: WorkerDashboardViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState
    val state = viewModel.state.value
    val context = LocalContext.current
    val spannerLottie by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.spanner_lottie)
    )

    LaunchedEffect(Unit) {

    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.oops_something_went_wrong
                    )
                }

                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(WorkerDashboardEvent.UpdateSelectedAddress)
                viewModel.onEvent(WorkerDashboardEvent.UpdateUserDetails)
            }

            else -> Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = ScaffoldBottomPaddingValue),
        color = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = SizeMedium,
                        start = SizeMedium,
                        end = SizeMedium,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DashboardSelectedAddressAndProfile(
                        profileImageUrl = Session.userSession.value?.profilePicture ?: "",
                        imageLoader = imageLoader,
                        onProfileClick = {
                            onNavigate(Screen.WorkerProfileScreen.route)
                        },
                        localAddress = state.selectedLocalAddress,
                        onLocationClick = {
                            onNavigate(Screen.SelectLocationScreen.route)
                        }
                    )
                    PrimaryHeader(text = stringResource(R.string.worker_dashboard))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(SizeExtraSmall))
                    OpenToWorkSwitch(
                        modifier = Modifier.fillMaxWidth(),
                        checked = state.profile?.openToWork == true,
                        onCheckedChange = {
                            viewModel.onEvent(WorkerDashboardEvent.ToggleOpenToWork(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(SizeExtraSmall))
                    HorizontalDivider()
                    SecondaryHeader(
                        modifier = Modifier.padding(
                            vertical = SizeMedium
                        ),
                        text = stringResource(R.string.work_proposals),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        moreOptionText = "Refresh",
                        moreOption = true,
                        moreOptionPainter = painterResource(id = R.drawable.ic_refresh),
                        onMoreOptionClick = {
                            viewModel.onEvent(WorkerDashboardEvent.LoadWorkProposal)
                        }
                    )
                }

                LazyColumn {
                    items(pagingState.items.size) { index ->
                        val workProposal = pagingState.items[index]
                        if (index >= pagingState.items.size - 1 &&
                            !pagingState.endReached &&
                            !pagingState.isLoading
                        ) {
                            viewModel.loadNextWorkers()
                        }
                        Box(
                            Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            WorkProposalCardActionRow(
                                actionIconSize = ACTION_ITEM_SIZE.dp,
                                onAccept = {
                                    viewModel.onEvent(
                                        WorkerDashboardEvent.AcceptWorkProposal(
                                            workProposal.workProposalId
                                        )
                                    )
                                },
                                onReject = {
                                    viewModel.onEvent(
                                        WorkerDashboardEvent.RejectWorkProposal(
                                            workProposal.workProposalId
                                        )
                                    )
                                }
                            )
                            WorkProposalDraggableCard(
                                workProposal = workProposal,
                                isRevealed = viewModel.revealedCard.value?.workProposalId == workProposal.workProposalId,
                                cardOffset = CARD_OFFSET.dp(),
                                imageLoader = imageLoader,
                                onExpand = { viewModel.onItemExpanded(workProposal) },
                                onCollapse = { viewModel.onItemCollapsed(workProposal) },
                                onClick = { viewModel.onItemExpanded(workProposal) }
                            )
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = state.profile?.isWorker == false
    ) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                onNavigateUp()
            }
        ) {
            PrimarySuccessDialogContent(
                title = stringResource(R.string.unregistered_as_worker_join_now),
                description = stringResource(R.string.showcase_skills_get_hired_by_others),
                buttonText = stringResource(R.string.register_as_worker),
                lottie = spannerLottie,
                cancelButtonText = stringResource(R.string.not_now),
                onCancelClick = {
                    onNavigateUp()
                },
                isCancelable = true,
                onButtonClick = {
                    onNavigate(Screen.RegisterAsWorkerScreen.route)
//                    viewModel.isRegisterCompleteDialogDisplayed.postValue(false)
//                    previousBackStackEntry?.savedStateHandle?.set(
//                        "isWorkerProfileUpdated",
//                        true
//                    )
//                    onNavigateUp()
                }
            )
        }
    }
}

