package com.fifty.fixitnow.featureworkproposal.domain.model

data class WorkProposal(
    val id: String,
    val userId: String,
    val workerId: String,
    val chosenCategoryId: String,
    val wage: Float,
    val isFullDay: Boolean,
    val isBeforeNoon: Boolean,
    val isAccepted: Boolean,
    val isRejected: Boolean,
    val status: Boolean,
    val formattedDate: String,
    val formattedProposedDate: String,
    val workDescription: String,
    val proposedAddressId: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean
)
