package com.fifty.workersportal.featurelocation.domain.model

import com.fifty.workersportal.featurelocation.data.remote.dto.GeocodeAddress

data class ReverseGeocodeDisplayAddress(
    val geocodeAddress: GeocodeAddress?,
    val display_name: String?,
    val lat: String?,
    val lon: String?
)