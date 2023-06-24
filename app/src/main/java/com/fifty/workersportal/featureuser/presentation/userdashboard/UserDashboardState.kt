package com.fifty.workersportal.featureuser.presentation.userdashboard

import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureuser.domain.model.Banner
import com.fifty.workersportal.featureuser.domain.model.UserProfile

data class UserDashboardState(
    val banners: List<Banner> = emptyList(),
    val selectedLocalAddress: LocalAddress? = null,
    val isLoading: Boolean = false
)