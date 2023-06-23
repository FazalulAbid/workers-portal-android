package com.fifty.workersportal.featureworker.presentation.workerdashboard

import com.fifty.workersportal.featureuser.domain.model.Profile

data class WorkerDashboardState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)
