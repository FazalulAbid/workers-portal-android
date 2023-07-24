package com.fifty.fixitnow.featureuser.presentation.userdashboard

import com.fifty.fixitnow.featureworker.domain.model.Category

sealed class UserDashboardEvent {
    data class ToggleFavouriteWorker(val workerId: String) : UserDashboardEvent()
    data class SelectWorkerCategory(val category: Category) : UserDashboardEvent()
    object UpdateSelectedAddress : UserDashboardEvent()
    object RefreshWorkers: UserDashboardEvent()
}
