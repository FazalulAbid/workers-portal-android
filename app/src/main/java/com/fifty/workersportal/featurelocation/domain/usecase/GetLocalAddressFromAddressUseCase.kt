package com.fifty.workersportal.featurelocation.domain.usecase

import android.location.Address
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress

class GetLocalAddressFromAddressUseCase {

    operator fun invoke(address: Address): LocalAddress {
        Log.d("Hello", "invoke: $address")
        return LocalAddress(
            id = "",
            title = "",
            completeAddress = "",
            floor = "",
            landmark = "",
            place = getLocalPlaceFromAddressLine(address.getAddressLine(0)).capitalize(Locale.current),
            subLocality = address.subLocality ?: "",
            city = address.locality ?: "",
            state = address.adminArea,
            country = address.countryName,
            pin = address.postalCode,
            location = listOf(address.latitude, address.longitude)
        )
    }

    private fun getLocalPlaceFromAddressLine(address: String): String {
        val startIndex = address.indexOf(",") + 1
//        val secondIndex = address.indexOf(",", startIndex)
        val endIndex = address.indexOf(",", startIndex + 1)

        return address.substring(startIndex + 1, endIndex)
            .trim()
    }
}