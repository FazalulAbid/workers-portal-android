package com.fifty.fixitnow.featurehistory.domain.model

import com.fifty.fixitnow.featurehistory.presentation.historyscreen.WorkHistoryStatus

data class WorkHistory(
    val id: String,
    val oppositeUserId: String,
    val wage: Float,
    val workType: String,
    val isAccepted: Boolean,
    val formattedProposedDate: String,
    val workDescription: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean,
    val isCompleted: Boolean,
    val workStatus: WorkHistoryStatus,
    val oppositeUserImageUrl: String,
    val oppositeFirstName: String,
    val oppositeLastName: String,
    val proposedDisplayAddress: String,
    val categoryTitle: String,
    val categorySkill: String,
    val isProposalSentByUser: Boolean
)
