package com.fifty.fixitnow.featureworkproposal.domain.model

import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress

data class WorkProposalForWorker(
    val workProposalId: String,
    val userId: String,
    val displayWage: String,
    val workType: String,
    val formattedProposedDate: String,
    val workDescription: String,
    val firstName: String,
    val lastName: String,
    val profileImageUrl: String,
    val categoryTitle: String,
    val categorySkill: String,
    val proposedAddress: LocalAddress,
    val formattedDateTime: String
)
