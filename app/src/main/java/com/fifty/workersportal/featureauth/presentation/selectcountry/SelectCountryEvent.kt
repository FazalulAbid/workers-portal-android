package com.fifty.workersportal.featureauth.presentation.selectcountry

import com.fifty.workersportal.featureauth.domain.model.Country

sealed class SelectCountryEvent {
    data class SearchQuery(val query: String) : SelectCountryEvent()
    data class SelectCountry(val country: Country) : SelectCountryEvent()
}
