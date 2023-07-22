package com.fifty.fixitnow.featureworkproposal.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworkproposal.domain.repository.WorkProposalRepository

class AcceptOrRejectWorkProposalUseCase(
    private val repository: WorkProposalRepository
) {

    suspend operator fun invoke(workProposalId: String, isAcceptProposal: Boolean): SimpleResource =
        repository.acceptOrRejectWorkProposal(
            workProposalId = workProposalId,
            isAcceptProposal = isAcceptProposal
        )
}