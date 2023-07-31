package com.fifty.fixitnow.featurehistory.data.remote

import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import com.fifty.fixitnow.featurelocation.data.remote.dto.LocalAddressDto
import com.google.gson.annotations.SerializedName

data class WorkHistoryDto(
    @SerializedName("_id")
    val id: String,
    val userId: String,
    val workerId: String,
    val wage: Int,
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
    fun toWorkHistory() =
        WorkHistory(
            id = id,
            oppositeUserId = if (isProposalSentByUser) userId else workerId,
            wage = wage,
            isFullDay = isFullDay,
            isBeforeNoon = isBeforeNoon,
            isAccepted = isAccepted,
            formattedProposedDate = proposedDate.toString(),
            workDescription = workDescription,
            isUserDeleted = isUserDeleted,
            isWorkerDeleted = isWorkerDeleted,
            isCompleted = isCompleted,
            oppositeUserImageUrl = if (isProposalSentByUser) userImageUrl else workerImageUrl,
            oppositeFirstName = oppositeFirstName,
            oppositeLastName = oppositeLastName,
            proposedAddress = proposedAddress,
            categoryTitle = categoryTitle,
            categorySkill = categorySkill,
            isProposalSentByUser = isProposalSentByUser
        )
}
