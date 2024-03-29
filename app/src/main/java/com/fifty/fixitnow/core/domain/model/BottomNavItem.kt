package com.fifty.fixitnow.core.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Painter,
    val iconSelected: Painter,
    val contentDescription: String? = null,
    val alertCount: Int? = null
)
