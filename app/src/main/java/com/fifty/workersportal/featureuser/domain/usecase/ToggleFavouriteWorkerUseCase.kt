package com.fifty.workersportal.featureuser.domain.usecase

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class ToggleFavouriteWorkerUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(userId: String, isFavourite: Boolean): SimpleResource =
        repository.toggleFavouriteWorker(userId, isFavourite)
}