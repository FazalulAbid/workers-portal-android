package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class GetWorkerDetailsUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(workerId: String): Resource<Worker> =
        repository.getWorkerDetails(workerId)
}