package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import com.fifty.workersportal.featureworkproposal.domain.model.WorkProposal

data class WorkProposalState(
    val workProposal: WorkProposal? = null,
    val isLoading: Boolean = false
)
