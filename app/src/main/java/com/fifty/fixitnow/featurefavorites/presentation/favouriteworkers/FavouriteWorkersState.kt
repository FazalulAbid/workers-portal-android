package com.fifty.fixitnow.featurefavorites.presentation.favouriteworkers

import com.fifty.fixitnow.featureworker.domain.model.Worker

data class FavouriteWorkersState(
    val favouriteWorkers: List<Worker> = emptyList(),
    val isLoading: Boolean = false
)
