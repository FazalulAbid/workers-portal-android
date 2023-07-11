package com.fifty.workersportal.featureuser.presentation.userdashboard

import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalEvent

sealed class UserDashboardEvent {
    data class ToggleFavouriteWorker(val value: Boolean) : UserDashboardEvent()
    data class SelectWorkerCategory(val category: Category) : UserDashboardEvent()
    object UpdateSelectedAddress : UserDashboardEvent()
}
