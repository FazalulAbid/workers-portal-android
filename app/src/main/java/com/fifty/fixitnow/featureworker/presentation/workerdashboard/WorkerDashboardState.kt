package com.fifty.fixitnow.featureworker.presentation.workerdashboard

import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featureuser.domain.model.Profile

data class WorkerDashboardState(
    val profile: Profile? = null,
    val selectedLocalAddress: LocalAddress? = null,
    val isLoading: Boolean = false
)
