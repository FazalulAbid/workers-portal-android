package com.fifty.workersportal.featurelocation.domain.model

import java.lang.StringBuilder

data class LocalAddress(
    val id: String,
    val title: String,
    val completeAddress: String,
    val floor: String?,
    val landmark: String?,
    val place: String?,
    val subLocality: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val pin: String?,
    val location: List<Double>
)

fun LocalAddress?.toDisplayAddress(): String? {
    val addressBuilder = StringBuilder()
    var count = 0
    if (this == null) {
        return null
    } else {
        if (place?.isNotBlank() == true) {
            addressBuilder.append("$place")
            count++
        }
        if (subLocality?.isNotBlank() == true) {
            addressBuilder.append(", $subLocality")
            count++
        }
        if (city?.isNotBlank() == true) {
            addressBuilder.append(", $city")
            count++
        }
        if (state?.isNotBlank() == true && count < 3) {
            addressBuilder.append(", $state")
            count++
        }
        if (country?.isNotBlank() == true && count < 3) {
            addressBuilder.append(", $country")
        }
        return addressBuilder.toString()
    }
}