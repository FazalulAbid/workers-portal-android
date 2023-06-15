package com.fifty.workersportal.featureworker.presentation.workerprofile

import com.fifty.workersportal.featureuser.domain.model.Profile

data class WorkerProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)
