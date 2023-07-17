package com.fifty.fixitnow.featurelocation.domain.model

import com.fifty.fixitnow.featurelocation.data.remote.dto.GeocodeAddress

data class ReverseGeocodeDisplayAddress(
    val geocodeAddress: GeocodeAddress?,
    val display_name: String?,
    val lat: String?,
    val lon: String?
)