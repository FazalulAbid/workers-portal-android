package com.fifty.workersportal.featureauth.presentation.selectcountry

import com.fifty.workersportal.featureauth.domain.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SelectCountryState(
    val isLoading: Boolean = false
)