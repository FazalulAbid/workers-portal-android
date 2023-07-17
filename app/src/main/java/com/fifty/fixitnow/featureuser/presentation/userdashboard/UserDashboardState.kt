package com.fifty.fixitnow.featureuser.presentation.userdashboard

import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featureuser.domain.model.Banner

data class UserDashboardState(
    val banners: List<Banner> = emptyList(),
    val selectedLocalAddress: LocalAddress? = null,
    val isLoading: Boolean = false
)