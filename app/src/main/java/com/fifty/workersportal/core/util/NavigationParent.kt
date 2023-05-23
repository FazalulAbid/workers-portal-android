package com.fifty.workersportal.core.util

sealed class NavigationParent(val label: String) {
    object Home : NavigationParent("Home")
    object Work : NavigationParent("Work")
    object Favorite : NavigationParent("Favorite")
    object History : NavigationParent("History")
}
