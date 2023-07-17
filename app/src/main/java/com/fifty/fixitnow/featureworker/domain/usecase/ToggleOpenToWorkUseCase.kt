package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class ToggleOpenToWorkUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(value: Boolean): SimpleResource =
        repository.toggleOpenToWork(value)
}