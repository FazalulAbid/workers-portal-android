package com.fifty.fixitnow.core.util

import com.fifty.fixitnow.featureworker.domain.model.Worker

interface FavouriteToggle {

    suspend fun toggleFavourite(
        workerId: String,
        workers: List<Worker>,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Worker>) -> Unit
    )
}