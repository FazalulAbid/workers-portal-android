package com.fifty.workersportal.core.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Painter,
    val contentDescription: String? = null,
    val alertCount: Int? = null
)
