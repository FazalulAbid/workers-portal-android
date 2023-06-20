package com.fifty.workersportal.featureworker.presentation.workerdashboard

sealed class WorkerDashboardEvent {
    data class ToggleOpenToWork(val value: Boolean) : WorkerDashboardEvent()
}
