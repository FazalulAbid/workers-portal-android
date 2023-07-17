package com.fifty.fixitnow.featureworkproposal.domain.model

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworkproposal.data.util.WorkProposalError

data class WorkProposalResult(
    val workDateError: WorkProposalError? = null,
    val chosenWorkerError: WorkProposalError? = null,
    val chosenCategoryError: WorkProposalError? = null,
    val workDescriptionError: WorkProposalError? = null,
    val workAddressError: WorkProposalError? = null,
    val workWageError: WorkProposalError? = null,
    val result: Resource<WorkProposal>? = null
)