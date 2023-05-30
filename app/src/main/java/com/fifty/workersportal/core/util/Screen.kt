package com.fifty.workersportal.core.util

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object SelectCountryScreen : Screen("select_country_screen")
    object OtpVerificationScreen : Screen("otp_verification_screen")
    object UserDashboardScreen : Screen("user_dashboard_screen")
    object WorkerDashboardScreen : Screen("worker_dashboard_screen")
    object FavoriteScreen : Screen("favorite_screen")
    object HistoryScreen : Screen("work_history_screen")
    object NotificationScreen : Screen("notification_screen")
    object SelectWorkerCategoryScreen : Screen("select_worker_category_screen")
    object WorkerListScreen : Screen("worker_list_screen")
    object WorkerProfileScreen : Screen("worker_profile_screen")
    object RegisterAsWorkerScreen : Screen("register_as_worker_screen")
}
