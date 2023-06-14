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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featurelocation.presentation.component.CurrentLocationMapSection
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

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@SuppressLint("MissingPermission")
@Composable
fun DetectCurrentLocationScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: DetectCurrentLocationViewModel = hiltViewModel()
) {
    var showSheet by remember { mutableStateOf(false) }
    val state = viewModel.state.value
    val context = LocalContext.current
    val initialZoom = 1f
    val finalZoom = 18f

    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()
    val focusManager = LocalFocusManager.current
    val addressTitleFocusRequester = remember { FocusRequester() }
    val completeAddressFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

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

                else -> Unit
            }
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

    val resolutionResult =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.onEvent(DetectCurrentLocationEvent.CurrentLocation)
            } else {
                onNavigateUp()
            }
        }

    val locationRequest =
        LocationRequest
            .Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(3000)
            .setMaxUpdateDelayMillis(100)
            .build()

    LaunchedEffect(Unit) {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task =
            LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

        task
            .addOnSuccessListener { response ->
                val locationState = response.locationSettingsStates
                if (locationState!!.isLocationPresent) {
                    viewModel.onEvent(DetectCurrentLocationEvent.CurrentLocation)
                }
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(exception.resolution).build()
                        resolutionResult.launch(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeMedium)
                .clip(RoundedCornerShape(topStart = SizeSmall, topEnd = SizeSmall))
        ) {
            if (state.isLoading || cameraPositionState.isMoving) {
                CircularProgressIndicator()
            } else {
                Row() {
                    Icon(
                        modifier = Modifier.size(SizeLarge),
                        painter = painterResource(id = R.drawable.ic_location_mark),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(SizeSmall))
                    Column {
                        Text(
                            text = if (state.localAddress?.place?.isBlank() == true) {
                                state.localAddress.subLocality ?: state.localAddress.city ?: ""
                            } else state.localAddress?.place ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = ("${state.localAddress?.subLocality}, " +
                                    "${state.localAddress?.city}"),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SizeMedium))
                PrimaryButton(text = stringResource(R.string.enter_complete_address)) {
                    viewModel.onEvent(
                        DetectCurrentLocationEvent.SelectCurrentCameraPosition(
                            lat = cameraPositionState.position.target.latitude,
                            lng = cameraPositionState.position.target.longitude
                        )
                    )
                    showSheet = true
                }
            }
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
                    onDone = {
                        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                        keyboardController?.hide()
                    },
                    focusManager = focusManager,
                    completeAddressFocusRequester = completeAddressFocusRequester,
                    addressTitleFocusRequester = addressTitleFocusRequester,
                )
            }
        }
    }
}