package com.fifty.workersportal.featurelocation.domain.usecase

import android.location.Address
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress

class GetLocalAddressFromAddressUseCase {

    operator fun invoke(address: Address): LocalAddress {
        return LocalAddress(
            id = "",
            title = "",
            houseName = "",
            place = getLocalPlaceFromAddressLine(address.getAddressLine(0)),
            subLocality = address.subLocality,
            city = address.locality,
            state = address.adminArea,
            country = address.countryName,
            pin = address.postalCode,
            location = listOf(address.latitude, address.longitude)
        )
    }

    private fun getLocalPlaceFromAddressLine(address: String): String {
        val startIndex = address.indexOf(",") + 1
        val secondIndex = address.indexOf(",", startIndex)
        val endIndex = address.indexOf(",", secondIndex + 1)

        return address.substring(secondIndex + 1, endIndex)
            .trim()
    }
}