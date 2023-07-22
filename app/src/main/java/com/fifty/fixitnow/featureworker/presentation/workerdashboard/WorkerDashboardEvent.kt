package com.fifty.fixitnow.featureworker.presentation.workerdashboard

sealed class WorkerDashboardEvent {
    data class ToggleOpenToWork(val value: Boolean) : WorkerDashboardEvent()
    data class AcceptWorkProposal(val workProposalId: String) : WorkerDashboardEvent()
    data class RejectWorkProposal(val workProposalId: String) : WorkerDashboardEvent()
    object UpdateSelectedAddress : WorkerDashboardEvent()
    object UpdateUserDetails : WorkerDashboardEvent()
    object LoadWorkProposal : WorkerDashboardEvent()
}
