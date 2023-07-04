package com.fifty.workersportal.featureworker.presentation.workerdashboard

sealed class WorkerDashboardEvent {
    data class ToggleOpenToWork(val value: Boolean) : WorkerDashboardEvent()
    object UpdateSelectedAddress : WorkerDashboardEvent()
    object UpdateUserDetails : WorkerDashboardEvent()
}
