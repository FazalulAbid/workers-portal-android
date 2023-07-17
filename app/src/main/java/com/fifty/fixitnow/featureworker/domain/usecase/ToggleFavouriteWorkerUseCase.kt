package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class ToggleFavouriteWorkerUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(userId: String, isFavourite: Boolean): SimpleResource =
        repository.toggleFavouriteWorker(userId, isFavourite)
}