package com.fifty.workersportal.featurelocation.presentation.component

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun CurrentLocationMapSection(
    state: DetectCurrentLocationState
) {
    val coroutineScope = rememberCoroutineScope()
    val mapProperties = MapProperties(
        isMyLocationEnabled = true
    )
    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = true,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true
    )

    val initialZoom = 1f
    val finalZoom = 15f
    val singapore = LatLng(1.35, 103.87)
    val sydney = LatLng(-33.852, 151.211)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    GoogleMap(
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings
    )

    Box(modifier = Modifier.padding(SizeLarge)) {
        GetCurrentLocationButton(
            text = stringResource(R.string.use_current_location),
            onClick = {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(sydney, finalZoom, 0f, 0f)
                        ),
                        durationMs = 2000
                    )
                }
            }
        )
    }
}