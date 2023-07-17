package com.fifty.fixitnow.featurelocation.presentation.selectlocation

import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress

data class SelectLocationState(
    val isLoading: Boolean = false,
    val localAddresses: List<LocalAddress> = emptyList()
)
