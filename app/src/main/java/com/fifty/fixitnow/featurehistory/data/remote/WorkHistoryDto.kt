package com.fifty.fixitnow.featurehistory.data.remote

import com.fifty.fixitnow.core.util.toFormattedDateWithDay
import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import com.fifty.fixitnow.featurehistory.presentation.historyscreen.WorkHistoryStatus
import com.fifty.fixitnow.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.fixitnow.featurelocation.domain.model.toDisplayAddress
import com.google.gson.annotations.SerializedName

data class WorkHistoryDto(
    @SerializedName("_id")
    val id: String,
    val userId: String,
    val workerId: String,
    val wage: Float,
    val isFullDay: Boolean,
    val isBeforeNoon: Boolean,
    val isAccepted: Boolean,
    val proposedDate: Long,
    val workDescription: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean,
    val isCompleted: Boolean,
    val userImageUrl: String,
    val workerImageUrl: String,
    val oppositeFirstName: String,
    val oppositeLastName: String,
    val proposedAddress: LocalAddressDto,
    val categoryTitle: String,
    val categorySkill: String,
    val isProposalSentByUser: Boolean
) {
    fun toWorkHistory(): WorkHistory {
        val workType = if (isFullDay) {
            "Full Day"
        } else {
            if (isBeforeNoon) "Before Noon" else "After Noon"
        }
        return WorkHistory(
            id = id,
            oppositeUserId = if (isProposalSentByUser) userId else workerId,
            wage = wage,
            workType = workType,
            isAccepted = isAccepted,
            formattedProposedDate = proposedDate.toFormattedDateWithDay(),
            workDescription = workDescription,
            isUserDeleted = isUserDeleted,
            isWorkerDeleted = isWorkerDeleted,
            isCompleted = isCompleted,
            oppositeUserImageUrl = if (isProposalSentByUser) userImageUrl else workerImageUrl,
            oppositeFirstName = oppositeFirstName,
            oppositeLastName = oppositeLastName,
            proposedDisplayAddress = if (isProposalSentByUser) proposedAddress.toLocalAddress()
                .toDisplayAddress() ?: ""
            else "Hired to ${proposedAddress.title}, ",
            categoryTitle = categoryTitle,
            categorySkill = categorySkill,
            isProposalSentByUser = isProposalSentByUser,
            workStatus = if (isCompleted) {
                WorkHistoryStatus.COMPLETED
            } else if (isAccepted) {
                WorkHistoryStatus.PENDING
            } else if (isUserDeleted) {
                WorkHistoryStatus.CANCELLED_BY_USER
            } else if (isWorkerDeleted) {
                WorkHistoryStatus.CANCELLED_BY_WORKER
            } else {
                WorkHistoryStatus.REJECTED
            }
        )
    }

}

