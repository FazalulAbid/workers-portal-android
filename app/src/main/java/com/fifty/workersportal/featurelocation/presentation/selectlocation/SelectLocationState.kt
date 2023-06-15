package com.fifty.workersportal.featurelocation.presentation.selectlocation

import com.fifty.workersportal.featurelocation.domain.model.LocalAddress

data class SelectLocationState(
    val isLoading: Boolean = false,
    val localAddresses: List<LocalAddress> = emptyList()
)
