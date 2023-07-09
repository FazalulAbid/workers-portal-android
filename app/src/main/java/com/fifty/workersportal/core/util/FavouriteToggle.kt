package com.fifty.workersportal.core.util

import com.fifty.workersportal.featureworker.domain.model.Worker

interface FavouriteToggle {

    suspend fun toggleFavourite(
        workerId: String,
        workers: List<Worker>,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Worker>) -> Unit
    )
}