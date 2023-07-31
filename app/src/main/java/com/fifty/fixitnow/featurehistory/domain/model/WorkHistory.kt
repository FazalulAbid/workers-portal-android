package com.fifty.fixitnow.featurehistory.domain.model

import com.fifty.fixitnow.featurelocation.data.remote.dto.LocalAddressDto

data class WorkHistory(
    val id: String,
    val oppositeUserId: String,
    val wage: Int,
    val isFullDay: Boolean,
    val isBeforeNoon: Boolean,
    val isAccepted: Boolean,
    val formattedProposedDate: String,
    val workDescription: String,
    val isUserDeleted: Boolean,
    val isWorkerDeleted: Boolean,
    val isCompleted: Boolean,
    val oppositeUserImageUrl: String,
    val oppositeFirstName: String,
    val oppositeLastName: String,
    val proposedAddress: LocalAddressDto,
    val categoryTitle: String,
    val categorySkill: String,
    val isProposalSentByUser: Boolean
)
