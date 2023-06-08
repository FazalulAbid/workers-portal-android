package com.fifty.workersportal.featureuser.presentation.userdashboard

import com.fifty.workersportal.featureuser.domain.model.Banner

data class DashboardBannerState(
    val banners: List<Banner> = emptyList(),
    val isLoading: Boolean = false
)
