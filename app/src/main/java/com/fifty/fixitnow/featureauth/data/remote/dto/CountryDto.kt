package com.fifty.fixitnow.featureauth.data.remote.dto

import com.fifty.fixitnow.featureauth.domain.model.Country

data class CountryDto(
    val alpha2Code: String,
    val callingCodes: List<String>,
    val flags: FlagDto,
    val independent: Boolean,
    val name: String
) {
    fun toCountry(): Country {
        return Country(
            alpha2Code = alpha2Code,
            callingCode = "+${callingCodes.first()}",
            flagUrl = flags.png ?: flags.svg ?: "",
            name = name
        )
    }
}
