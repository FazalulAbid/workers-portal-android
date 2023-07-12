package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class GetWorkerDetailsUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(workerId: String): Resource<Worker> =
        repository.getWorkerDetails(workerId)
}