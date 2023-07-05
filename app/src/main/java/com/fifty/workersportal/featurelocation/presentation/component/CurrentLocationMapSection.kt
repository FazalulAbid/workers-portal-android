package com.fifty.workersportal.featurelocation.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationState
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("MissingPermission")
@Composable
fun CurrentLocationMapSection(
    state: DetectCurrentLocationState,
    cameraPositionState: CameraPositionState,
    onClickCurrentLocation: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val mapProperties = MapProperties(
        isMyLocationEnabled = true
    )
    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = true,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true
    )
    GoogleMap(
        properties = mapProperties.copy(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings
    )

    Box(modifier = Modifier.padding(SizeLarge)) {
        GetCurrentLocationButton(
            text = stringResource(R.string.use_current_location),
            onClick = onClickCurrentLocation
        )
    }
}