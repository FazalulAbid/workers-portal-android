package com.fifty.fixitnow.featureuser.presentation.userdashboard

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.component.CoarseLocationPermissionTextProvider
import com.fifty.fixitnow.core.presentation.component.DashboardSelectedAddressAndProfile
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.PermissionDialog
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.OnLifecycleEvent
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.core.util.openAppSettings
import com.fifty.fixitnow.core.util.toMillis
import com.fifty.fixitnow.featureuser.presentation.component.AutoSlidingCarousal
import com.fifty.fixitnow.featureuser.presentation.component.DashboardGreetingText
import com.fifty.fixitnow.featureuser.presentation.component.MostBookedServicesItem
import com.fifty.fixitnow.featureuser.presentation.component.SearchWorkerButton
import com.fifty.fixitnow.featureuser.presentation.component.SuggestedCategoryItem
import com.fifty.fixitnow.featureworker.presentation.component.WorkerItem
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalEvent
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(
    ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun UserDashboardScreen(
    onNavigate: (String) -> Unit = {},
    imageLoader: ImageLoader,
    userDashboardViewModel: UserDashboardViewModel = hiltViewModel(),
    workProposalViewModel: WorkProposalViewModel
) {
    val pagingState = userDashboardViewModel.pagingState
    val state = userDashboardViewModel.state.value
    val context = LocalContext.current
    val calenderState = rememberUseCaseState()

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                userDashboardViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

//    OnLifecycleEvent { _, event ->
//        when (event) {
//            Lifecycle.Event.ON_RESUME -> {
//                userDashboardViewModel.onEvent(UserDashboardEvent.UpdateSelectedAddress)
//                userDashboardViewModel.onEvent(UserDashboardEvent.RefreshWorkers)
//            }
//
//            else -> Unit
//        }
//    }

    LaunchedEffect(key1 = true) {
        userDashboardViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                else -> {}
            }
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(SizeMedium)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(SizeMedium)
                    ) {
                        DashboardGreetingText(
                            name = if (Session.userSession.value?.firstName.isNullOrBlank()) {
                                stringResource(id = R.string.there)
                            } else Session.userSession.value?.firstName ?: "",
                            greetingText = stringResource(R.string.dashboard_greeting_prefix)
                        )
                        Spacer(Modifier.height(SizeExtraSmall))
                        DashboardSelectedAddressAndProfile(
                            profileImageUrl = Session.userSession.value?.profilePicture ?: "",
                            imageLoader = imageLoader,
                            onProfileClick = {
                                onNavigate(Screen.UserProfileScreen.route)
                            },
                            localAddress = state.selectedLocalAddress,
                            onLocationClick = {
                                multiplePermissionResultLauncher.launch(
                                    permissionsToRequest
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(SizeMedium))
                        SearchWorkerButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(MediumButtonHeight),
                            text = stringResource(id = R.string.search_aryan),
                            onClick = {
                                onNavigate(Screen.SearchWorkerScreen.route)
                            }
                        )
                    }
                }
                item {
                    Column {
                        AutoSlidingCarousal(
                            banners = state.banners,
                            imageLoader = imageLoader
                        )
                    }
                }
                item {
                    Column(Modifier.padding(horizontal = SizeMedium)) {
                        SecondaryHeader(
                            text = stringResource(R.string.suggested_categories),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            moreOption = true,
                            moreOptionText = stringResource(R.string.all_categories),
                            onMoreOptionClick = {
                                onNavigate(Screen.SearchCategoryScreen.route)
                            }
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyRow(
                            Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(SizeMedium),
                            horizontalArrangement = Arrangement.spacedBy(SizeMedium)
                        ) {
                            items(userDashboardViewModel.suggestedCategoriesState.value.suggestedCategories) {
                                SuggestedCategoryItem(
                                    category = it,
                                    imageLoader = imageLoader
                                ) {
                                    userDashboardViewModel.onEvent(
                                        UserDashboardEvent.SelectWorkerCategory(it)
                                    )
                                    calenderState.show()
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
                item {
                    Column(Modifier.padding(horizontal = SizeMedium)) {
                        SecondaryHeader(
                            text = stringResource(R.string.most_booked_services),
                            modifier = Modifier.padding(bottom = SizeSmall),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyRow(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(SizeSmall),
                            contentPadding = PaddingValues(horizontal = SizeSmall)
                        ) {
                            items(pagingState.items.size) { index ->
                                val worker = pagingState.items[index]
                                if (index >= pagingState.items.size - 1 &&
                                    !pagingState.endReached &&
                                    !pagingState.isLoading
                                ) {
                                    userDashboardViewModel.loadNextWorkers()
                                }
                                MostBookedServicesItem(
                                    worker = worker,
                                    imageLoader = imageLoader,
                                    onFavouriteClick = {
                                        userDashboardViewModel.onEvent(
                                            UserDashboardEvent.ToggleFavouriteWorker(worker.workerId)
                                        )
                                    },
                                    onClick = {
                                        onNavigate(Screen.WorkerProfileScreen.route + "?userId=${worker.workerId}")
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(SizeMedium))
                        HorizontalDivider()
                    }
                }
                item {
                    Column(Modifier.padding(horizontal = SizeMedium)) {
                        SecondaryHeader(
                            text = stringResource(R.string.explore_more),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                items(pagingState.items.size) { index ->
                    val worker = pagingState.items[index]
                    if (index >= pagingState.items.size - 1 &&
                        !pagingState.endReached &&
                        !pagingState.isLoading
                    ) {
                        userDashboardViewModel.loadNextWorkers()
                    }
                    WorkerItem(
                        modifier = Modifier.padding(horizontal = SizeMedium),
                        worker = worker,
                        imageLoader = imageLoader,
                        isFavouriteButtonNeeded = false,
                        onFavouriteClick = {
                            userDashboardViewModel.onEvent(
                                UserDashboardEvent.ToggleFavouriteWorker(worker.workerId)
                            )
                        },
                        onClick = {
                            onNavigate(Screen.WorkerProfileScreen.route + "?userId=${worker.workerId}")
                        }
                    )
                }
                item {
                    if (pagingState.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SizeMedium),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    userDashboardViewModel.visiblePermissionDialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> CoarseLocationPermissionTextProvider()
                else -> {
                    return@forEach
                }
            },
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = userDashboardViewModel::dismissPermissionDialog,
            onOkClick = {
                userDashboardViewModel.dismissPermissionDialog()
                multiplePermissionResultLauncher.launch(
                    arrayOf(permission)
                )
            },
            onGotoAppSettingsClick = {
                context.openAppSettings()
                userDashboardViewModel.dismissPermissionDialog()
            }
        )
    }

    val rangeStartDate = LocalDate.now().plusDays(1)
    val rangeEndYearOffset = 1L
    val rangeEndDate = LocalDate.now().plusYears(rangeEndYearOffset)
        .withMonth(1)
        .withDayOfMonth(15)
    val range = rangeStartDate..rangeEndDate

    CalendarDialog(
        state = calenderState,
        config = CalendarConfig(
            monthSelection = true,
            boundary = range
        ),
        selection = CalendarSelection.Date { date ->
            workProposalViewModel.onEvent(WorkProposalEvent.InputProposalDate(date))
            onNavigate(
                Screen.SearchWorkerScreen.route + "?categoryId=${userDashboardViewModel.selectedCategoryState.value?.id}?availabilityDate=${
                    date.toMillis()
                }"
            )
        }
    )
}