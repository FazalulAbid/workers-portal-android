package com.fifty.fixitnow.featureworker.presentation.workerdashboard

import com.fifty.fixitnow.core.util.Event

sealed class WorkerDashboardUiEvent : Event() {
    object WorkProposalAccepted : WorkerDashboardUiEvent()
    object WorkProposalRejected : WorkerDashboardUiEvent()
}
