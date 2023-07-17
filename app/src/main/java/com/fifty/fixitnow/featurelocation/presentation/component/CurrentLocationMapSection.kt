package com.fifty.fixitnow.featurelocation.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationState
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

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