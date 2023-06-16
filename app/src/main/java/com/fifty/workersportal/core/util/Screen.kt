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
    object SearchCategoryScreen : Screen("search_category_screen")
    object WorkerListScreen : Screen("worker_list_screen")
    object WorkerProfileScreen : Screen("worker_profile_screen")
    object ReviewAndRatingScreen : Screen("rating_and_review_screen")
    object RegisterAsWorkerScreen : Screen("register_as_worker_screen")
    object ChatScreen : Screen("chat_screen")
    object MessageScreen : Screen("message_screen")
    object SelectLocationScreen : Screen("select_location_screen")
    object DetectCurrentLocationScreen : Screen("detect_current_location_screen")
}
