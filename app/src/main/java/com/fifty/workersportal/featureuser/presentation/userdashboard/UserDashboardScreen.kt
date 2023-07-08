package com.fifty.workersportal.featureuser.presentation.userdashboard

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.component.CoarseLocationPermissionTextProvider
import com.fifty.workersportal.core.presentation.component.DashboardSelectedAddressAndProfile
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PermissionDialog
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.OnLifecycleEvent
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.core.util.openAppSettings
import com.fifty.workersportal.featureuser.presentation.component.AutoSlidingCarousal
import com.fifty.workersportal.featureuser.presentation.component.DashboardGreetingText
import com.fifty.workersportal.featureuser.presentation.component.MostBookedServicesItem
import com.fifty.workersportal.featureuser.presentation.component.SearchWorkerButton
import com.fifty.workersportal.featureuser.presentation.component.SuggestedCategoryItem
import com.fifty.workersportal.featureworker.presentation.component.WorkerListItem
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalEvent
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
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
    val state = userDashboardViewModel.state.value
    val context = LocalContext.current
    val calenderState = rememberUseCaseState()

    val favouriteLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.favourite_button_lottie)
    )

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

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                userDashboardViewModel.onEvent(UserDashboardEvent.UpdateSelectedAddress)
            }

            else -> Unit
        }
    }

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
            LazyColumn {
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
                        Spacer(modifier = Modifier.height(SizeSmall))
                        AutoSlidingCarousal(
                            banners = state.banners,
                            imageLoader = imageLoader
                        )
                        Spacer(modifier = Modifier.height(SizeSmall))
                    }
                }
                item {
                    Column(Modifier.padding(horizontal = SizeMedium)) {
                        SecondaryHeader(
                            text = stringResource(R.string.suggested_categories),
                            modifier = Modifier.padding(top = SizeMedium, bottom = SizeSmall),
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
                        LazyRow(Modifier.fillMaxWidth()) {
                            items(userDashboardViewModel.suggestedCategoriesState.value.suggestedCategories) {
                                SuggestedCategoryItem(
                                    category = it,
                                    imageLoader = imageLoader,
                                    imageSize = MediumProfilePictureHeight
                                ) {
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
                            modifier = Modifier.padding(top = SizeMedium, bottom = SizeSmall),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
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
                            items(10) {
                                MostBookedServicesItem()
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
                            modifier = Modifier.padding(top = SizeMedium, bottom = SizeSmall),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                items(10) {
                    WorkerListItem(
                        isFavourite = userDashboardViewModel._isFavourite.value,
                        lottieComposition = favouriteLottieComposition,
                        onFavouriteClick = {
                            userDashboardViewModel._isFavourite.value =
                                !userDashboardViewModel._isFavourite.value
                            //viewModel.onEvent(UserDashboardEvent.ToggleFavouriteWorker(it))
                        }
                    )
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
            onNavigate(Screen.SearchWorkerScreen.route)
        }
    )
}