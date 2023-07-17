package com.fifty.fixitnow.featureauth.presentation.selectcountry

import com.fifty.fixitnow.featureauth.domain.model.Country

sealed class SelectCountryEvent {
    data class SearchQuery(val query: String) : SelectCountryEvent()
    data class SelectCountry(val country: Country) : SelectCountryEvent()
}
