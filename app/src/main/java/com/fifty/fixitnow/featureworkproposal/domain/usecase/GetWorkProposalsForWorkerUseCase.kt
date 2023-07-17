package com.fifty.fixitnow.featureworkproposal.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworkproposal.domain.repository.WorkProposalRepository

class GetWorkProposalsForWorkerUseCase(
    private val repository: WorkProposalRepository
) {

    suspend operator fun invoke(page: Int): SimpleResource =
        repository.getWorkProposalsForWorker(page)
}