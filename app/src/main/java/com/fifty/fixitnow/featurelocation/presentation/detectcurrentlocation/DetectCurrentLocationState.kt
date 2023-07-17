package com.fifty.fixitnow.featurelocation.presentation.detectcurrentlocation

import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.google.android.gms.maps.model.LatLng

data class DetectCurrentLocationState(
    val currentLatLong: LatLng = LatLng(0.0, 0.0),
    val localAddress: LocalAddress? = null,
    val isLoading: Boolean = false,
    val isAddressLoading: Boolean = false
)
