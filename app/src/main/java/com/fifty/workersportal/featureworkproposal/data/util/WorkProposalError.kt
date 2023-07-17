package com.fifty.workersportal.featureworkproposal.data.util

sealed class WorkProposalError : Error() {
    object InvalidDate : WorkProposalError()
    object InvalidWorker : WorkProposalError()
    object InvalidWorkCategory : WorkProposalError()
    object InvalidWorkDescription : WorkProposalError()
    object InvalidWage : WorkProposalError()
    object WorkerDateConflict : WorkProposalError()
    object InvalidWorkAddress : WorkProposalError()
}
