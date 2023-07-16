package com.fifty.workersportal.featureworkproposal.domain.model

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworkproposal.data.util.WorkProposalError

data class WorkProposalResult(
    val workDateError: WorkProposalError? = null,
    val chosenWorkerError: WorkProposalError? = null,
    val chosenCategoryError: WorkProposalError? = null,
    val workDescriptionError: WorkProposalError? = null,
    val workAddressError: WorkProposalError? = null,
    val workWageError: WorkProposalError? = null,
    val result: Resource<WorkProposal>? = null
)