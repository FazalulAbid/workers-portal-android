package com.fifty.workersportal.featureworker.presentation.registerasworker

import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureuser.domain.model.Profile

data class UpdateWorkerState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)
