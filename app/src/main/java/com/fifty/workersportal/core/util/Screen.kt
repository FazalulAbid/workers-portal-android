package com.fifty.workersportal.core.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object AuthScreen : Screen("auth_screen")
}
