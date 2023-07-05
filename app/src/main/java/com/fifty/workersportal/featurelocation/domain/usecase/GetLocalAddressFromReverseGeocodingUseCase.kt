package com.fifty.workersportal.featurelocation.domain.usecase

import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.model.ReverseGeocodeDisplayAddress

class GetLocalAddressFromReverseGeocodingUseCase {

    operator fun invoke(reverseGeocodeDisplayAddress: ReverseGeocodeDisplayAddress): LocalAddress {
        return LocalAddress(
            id = "",
            title = "",
            completeAddress = "",
            floor = "",
            landmark = "",
            place = getSubstringBetweenCommas(
                reverseGeocodeDisplayAddress.display_name,
                0, 1
            ),
            subLocality = getSubstringBetweenCommas(
                reverseGeocodeDisplayAddress.display_name,
                2,
                2
            ),
            city = getSubstringBetweenCommas(reverseGeocodeDisplayAddress.display_name, 3, 3),
            state = reverseGeocodeDisplayAddress.address?.state,
            country = reverseGeocodeDisplayAddress.address?.country,
            pin = reverseGeocodeDisplayAddress.address?.postcode,
            location = listOf(
                reverseGeocodeDisplayAddress.lat?.toDouble() ?: 0.0,
                reverseGeocodeDisplayAddress.lon?.toDouble() ?: 0.0
            )
        )
    }

    private fun getSubstringBetweenCommas(
        input: String?,
        startCommaIndex: Int,
        endCommaIndex: Int
    ): String {
        return try {
            val substrings = input?.split(',')
            val extractedSubstring = substrings?.subList(startCommaIndex, endCommaIndex + 1)
            extractedSubstring?.joinToString(",")?.trim() ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}