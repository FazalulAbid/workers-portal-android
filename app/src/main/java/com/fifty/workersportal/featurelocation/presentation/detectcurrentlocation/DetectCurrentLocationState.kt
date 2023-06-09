package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import android.location.Location
import com.google.maps.android.compose.MapProperties

data class DetectCurrentLocationState(
    val lastKnowLocation: Location? = null
)
