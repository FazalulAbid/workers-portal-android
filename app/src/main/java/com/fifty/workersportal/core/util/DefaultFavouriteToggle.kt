package com.fifty.workersportal.core.util

import com.fifty.workersportal.featureworker.domain.model.Worker

class DefaultFavouriteToggle : FavouriteToggle {

    override suspend fun toggleFavourite(
        workerId: String,
        workers: List<Worker>,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Worker>) -> Unit
    ) {
        val likedWorker = workers.find { it.workerId == workerId }
        val isCurrentlyFavourite = likedWorker?.isFavourite == true
        val newWorkers = workers.map { worker ->
            if (worker.workerId == workerId) {
                worker.copy(
                    isFavourite = !worker.isFavourite
                )
            } else worker
        }
        onStateUpdated(newWorkers)
        when (onRequest(isCurrentlyFavourite)) {
            is Resource.Success -> Unit

            is Resource.Error -> {
                val oldWorkers = workers.map { worker ->
                    if (worker.workerId == workerId) {
                        worker.copy(
                            isFavourite = isCurrentlyFavourite
                        )
                    } else worker
                }
                onStateUpdated(oldWorkers)
            }
        }
    }
}