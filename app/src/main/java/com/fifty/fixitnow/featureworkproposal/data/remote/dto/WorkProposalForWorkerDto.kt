package com.fifty.fixitnow.featureworkproposal.data.remote.dto

import com.fifty.fixitnow.core.util.Constants.CURRENCY_SYMBOL
import com.fifty.fixitnow.core.util.toFormattedDateWithDay
import com.fifty.fixitnow.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalForWorker
import com.google.gson.annotations.SerializedName

data class WorkProposalForWorkerDto(
    @SerializedName("_id")
    val workProposalId: String,
    val userId: String,
    val wage: Double,
    val isFullDay: Boolean,
    val isBeforeNoon: Boolean,
    val proposedDate: Long,
    val workDescription: String,
    val firstName: String,
    val lastName: String,
    val profileImageUrl: String,
    val categoryTitle: String,
    val categorySkill: String,
    @SerializedName("address")
    val proposedAddress: LocalAddressDto,
    val timestamp: Long
) {
    fun toWorkProposalForWorker(): WorkProposalForWorker {
        val displayWage = "$CURRENCY_SYMBOL${wage}/${
            if (isFullDay) {
                "Day"
            } else {
                "Half Day"
            }
        }"
        return WorkProposalForWorker(
            workProposalId = workProposalId,
            userId = userId,
            displayWage = displayWage,
            workType = if (isFullDay) {
                "Full Day"
            } else {
                if (isBeforeNoon) {
                    "Before Noon"
                } else "After Noon"
            },
            formattedProposedDate = proposedDate.toFormattedDateWithDay(),
            workDescription = workDescription,
            firstName = firstName,
            lastName = lastName,
            profileImageUrl = profileImageUrl,
            categoryTitle = categoryTitle,
            categorySkill = categorySkill,
            proposedAddress = proposedAddress.toLocalAddress(),
            formattedDateTime = timestamp.toFormattedDateWithDay()
        )
    }
}



