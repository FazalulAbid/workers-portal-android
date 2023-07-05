package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featurelocation.presentation.component.CurrentLocationMapSection
import com.fifty.workersportal.featurelocation.presentation.component.PlaceAndAddressButtonSection
import com.fifty.workersportal.featurelocation.util.AddressError
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun DetectCurrentLocationScreen(
    onNavigateUp: () -> Unit = {},
    previousBackStackEntry: NavBackStackEntry?,
    viewModel: DetectCurrentLocationViewModel = hiltViewModel()
) {
    var showSheet by remember { mutableStateOf(false) }
    val state = viewModel.state.value
    val context = LocalContext.current
    val finalZoom = 18f

    val cameraPositionState = rememberCameraPositionState()
    val addressTitleFocusRequester = remember { FocusRequester() }
    val completeAddressFocusRequester = remember { FocusRequester() }
    val floorFocusRequester = remember { FocusRequester() }
    val landmarkFocusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UiEvent.OnCurrentLocation -> {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                viewModel.state.value.currentLatLong,
                                finalZoom, 0f, 0f
                            )
                        ),
                        durationMs = 1000
                    )
                }

                UiEvent.NavigateUp -> {
                    showSheet = false
                    previousBackStackEntry?.savedStateHandle?.set("isRefreshNeeded", true)
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = cameraPositionState.position) {
        if ((!cameraPositionState.isMoving) &&
            state.currentLatLong.latitude != 0.0 &&
            state.currentLatLong.longitude != 0.0
        ) {
            viewModel.onEvent(
                DetectCurrentLocationEvent.SelectCurrentCameraPosition(
                    lat = cameraPositionState.position.target.latitude,
                    lng = cameraPositionState.position.target.longitude
                )
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                AddressError.EmptyAddressTitle -> {
                    makeToast(R.string.address_title_field_can_t_be_empty, context)
                    addressTitleFocusRequester.requestFocus()
                }

                AddressError.EmptyCompleteAddress -> {
                    makeToast(R.string.address_field_can_t_be_empty, context)
                    completeAddressFocusRequester.requestFocus()
                }

                AddressError.EmptyCompleteAddressInputTooShort -> {
                    makeToast(R.string.address_too_short, Constants.MINIMUM_ADDRESS_LENGTH, context)
                    completeAddressFocusRequester.requestFocus()
                }
            }
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
                    text = stringResource(R.string.choose_your_location),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            CurrentLocationMapSection(
                state = viewModel.state.value,
                cameraPositionState = cameraPositionState,
                onClickCurrentLocation = {
                    viewModel.onEvent(DetectCurrentLocationEvent.CurrentLocation)
                }
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(LargeButtonHeight)
                        .offset(y = -LargeButtonHeight / 2f),
                    painter = painterResource(id = R.drawable.ic_location_mark),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        PlaceAndAddressButtonSection(
            cameraPositionState = cameraPositionState,
            state = state
        ) {
            showSheet = true
        }

        if (showSheet) {
            StandardBottomSheet(
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onDismiss = {
                    showSheet = false
                }
            ) {
                DetectCurrentLocationBottomSheetContent(
                    viewModel = viewModel,
                    onSaveAddressClick = {
                        viewModel.onEvent(DetectCurrentLocationEvent.SaveAddress)
                    },
                    completeAddressFocusRequester = completeAddressFocusRequester,
                    addressTitleFocusRequester = addressTitleFocusRequester,
                    floorFocusRequester = floorFocusRequester,
                    landmarkFocusRequester = landmarkFocusRequester,
                    isLoading = viewModel.state.value.isAddressLoading
                )
            }
        }
    }
}