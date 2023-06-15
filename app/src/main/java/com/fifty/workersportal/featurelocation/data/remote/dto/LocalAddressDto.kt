package com.fifty.workersportal.featurelocation.data.remote.dto

import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.google.gson.annotations.SerializedName

data class LocalAddressDto(
    @SerializedName("_id")
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
    val location: List<Double>,
    @SerializedName("__v")
    val versionKey: String
) {
    fun toLocalAddress(): LocalAddress {
        return LocalAddress(
            id = id,
            title = title,
            completeAddress = completeAddress,
            floor = floor,
            landmark = landmark,
            place = place,
            subLocality = subLocality,
            city = city,
            state = state,
            country = country,
            pin = pin,
            location = location
        )
    }
}