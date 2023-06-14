package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import android.location.Address
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.google.android.gms.maps.model.LatLng

data class DetectCurrentLocationState(
    val currentLatLong: LatLng = LatLng(0.0, 0.0),
    val address: Address? = null,
    val localAddress: LocalAddress? = null,
    val isLoading: Boolean = false,
    val isAddressLoading: Boolean = false
)
