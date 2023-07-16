package com.fifty.workersportal.featureworkproposal.domain.model

import java.time.LocalDateTime

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
    val timestamp: Long,
    val proposedDate: Long,
    val workDescription: String,
    val proposedAddressId: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean
)
