package com.fifty.fixitnow.core.util

sealed class NavigationParent(val route: String) {
    object OnBoarding : NavigationParent("OnBoarding")
    object Auth : NavigationParent("Auth")
    object Home : NavigationParent("Home")
    object Work : NavigationParent("Work")
    object Favorite : NavigationParent("Favorite")
    object History : NavigationParent("History")
    object Chat : NavigationParent("Chat")
}
