package com.fifty.workersportal.featureuser.presentation.userdashboard

sealed class UserDashboardEvent {
    data class ToggleFavouriteWorker(val value: Boolean) : UserDashboardEvent()
}
