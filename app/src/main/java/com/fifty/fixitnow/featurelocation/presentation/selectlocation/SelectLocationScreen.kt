package com.fifty.fixitnow.featurelocation.presentation.selectlocation

import android.app.Activity
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.EmptyListLottie
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featurelocation.presentation.component.DetectLocationButton
import com.fifty.fixitnow.featurelocation.presentation.component.SavedAddressItem
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectLocationScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    isRefreshNeeded: Boolean,
    viewModel: SelectLocationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }

    val resolutionResult =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onNavigate(Screen.DetectCurrentLocationScreen.route)
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
        if (isRefreshNeeded) {
            viewModel.onEvent(SelectLocationEvent.RefreshAddressList)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppBar(
                onNavigateUp = onNavigateUp,
                showBackArrow = true,
                title = {
                    Text(
                        text = stringResource(R.string.select_a_location),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            DetectLocationButton(
                text = stringResource(R.string.detect_your_current_location),
                description = stringResource(R.string.tap_here_to_detect_your_current_location_and_save_it_as_a_new_address),
                onClick = {
                    val builder = LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest)
                    val task =
                        LocationServices.getSettingsClient(context)
                            .checkLocationSettings(builder.build())

                    task
                        .addOnSuccessListener { response ->
                            val locationState = response.locationSettingsStates
                            if (locationState?.isLocationPresent == true) {
                                onNavigate(Screen.DetectCurrentLocationScreen.route)
                            }
                        }
                        .addOnFailureListener { exception ->
                            if (exception is ResolvableApiException) {
                                try {
                                    val intentSenderRequest =
                                        IntentSenderRequest.Builder(exception.resolution)
                                            .build()
                                    resolutionResult.launch(intentSenderRequest)
                                } catch (sendEx: IntentSender.SendIntentException) {
                                    // Ignore the error.
                                }
                            }
                        }
                }
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            SecondaryHeader(
                modifier = Modifier.padding(
                    horizontal = SizeMedium,
                    vertical = SizeSmall
                ),
                text = stringResource(R.string.saved_addresses),
                style = MaterialTheme.typography.titleMedium
            )
            if (state.localAddresses.isNotEmpty()) {
                LazyColumn {
                    items(state.localAddresses) { localAddress ->
                        SavedAddressItem(
                            localAddress = localAddress,
                            onClick = {
                                viewModel.onEvent(
                                    SelectLocationEvent.SelectLocalAddress(
                                        localAddress.id
                                    )
                                )
                            },
                            onMoreClick = { },
                            onShareClick = {}
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyListLottie()
                }
            }
        }
    }
}