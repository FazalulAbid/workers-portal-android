package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal

data class WorkProposalState(
    val workProposal: WorkProposal? = null,
    val isLoading: Boolean = false
)
