package com.fifty.fixitnow.featureworker.presentation.registerasworker

import com.fifty.fixitnow.featureuser.domain.model.Profile

data class UpdateWorkerState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val loadingText: Int? = null
)
