package com.fifty.workersportal.core.util

sealed class NavigationItem(val label: String) {
    object Home : NavigationItem("Home")
    object Work : NavigationItem("Work")
    object Favorite : NavigationItem("Favorite")
    object History : NavigationItem("History")
}
