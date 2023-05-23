package com.fifty.workersportal.core.util

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object SelectCountryScreen : Screen("select_country_screen")
    object OtpScreen : Screen("otp_screen")
    object UserDashboardScreen : Screen("user_dashboard_screen")
    object WorkerDashboardScreen : Screen("worker_dashboard_screen")
    object FavoriteScreen : Screen("favorite_screen")
    object HistoryScreen : Screen("work_history_screen")
    object NotificationScreen : Screen("notification_screen")
}
