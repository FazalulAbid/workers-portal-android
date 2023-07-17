package com.fifty.fixitnow.featurelocation.data.remote.dto

import com.fifty.fixitnow.featurelocation.domain.model.ReverseGeocodeDisplayAddress

data class ReverseGeocodeDisplayAddressDto(
    val address: GeocodeAddress,
    val display_name: String,
    val lat: String,
    val licence: String,
    val lon: String,
    val osm_id: Long,
    val osm_type: String,
    val place_id: Int,
    val powered_by: String
) {
    fun toReverseGeocodeDisplayAddress(): ReverseGeocodeDisplayAddress {
        return ReverseGeocodeDisplayAddress(
            geocodeAddress = address,
            display_name = display_name,
            lat = lat,
            lon = lon
        )
    }
}

data class GeocodeAddress(
    val country: String?,
    val country_code: String?,
    val postcode: String?,
    val state: String?,
    val state_district: String?,
)