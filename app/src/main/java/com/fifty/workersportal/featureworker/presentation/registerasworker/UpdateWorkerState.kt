package com.fifty.workersportal.featureworker.presentation.registerasworker

import com.fifty.workersportal.core.util.UiText

data class UpdateWorkerState(
    val successful:Boolean? = null,
    val message: UiText? = null,
    val isLoading: Boolean = false
)
