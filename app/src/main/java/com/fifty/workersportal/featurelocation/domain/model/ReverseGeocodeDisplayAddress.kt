package com.fifty.workersportal.featurelocation.domain.model

import com.fifty.workersportal.featurelocation.data.remote.dto.Address

data class ReverseGeocodeDisplayAddress(
    val address: Address?,
    val display_name: String?,
    val lat: String?,
    val lon: String?
)