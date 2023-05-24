package com.fifty.workersportal.featureauth.presentation.selectcountry

import com.fifty.workersportal.featureauth.domain.model.Country

data class SelectCountryState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false
)
