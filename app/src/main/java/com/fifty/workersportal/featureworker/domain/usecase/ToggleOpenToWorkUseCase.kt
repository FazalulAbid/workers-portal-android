package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class ToggleOpenToWorkUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(value: Boolean): SimpleResource =
        repository.toggleOpenToWork(value)
}