package com.fifty.fixitnow.featureworkproposal.data.remote.dto

import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal
import com.google.gson.annotations.SerializedName

data class WorkProposalDto(
    @SerializedName("_id")
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
    val timestamp: Any,
    val proposedDate: Any,
    val workDescription: String,
    val proposedAddressId: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean,
    @SerializedName("__v")
    val version: Int
) {
    fun toWorkProposal(): WorkProposal =
        WorkProposal(
            id = id,
            userId = userId,
            workerId = workerId,
            chosenCategoryId = chosenCategoryId,
            wage = wage,
            isFullDay = isFullDay,
            isBeforeNoon = isBeforeNoon,
            isAccepted = isAccepted,
            isRejected = isRejected,
            status = status,
            formattedDate = "Pending",
            formattedProposedDate = "Pending",
            workDescription = workDescription,
            proposedAddressId = proposedAddressId,
            isUserDeleted = isUserDeleted,
            isWorkerDeleted = isWorkerDeleted,
        )
}
