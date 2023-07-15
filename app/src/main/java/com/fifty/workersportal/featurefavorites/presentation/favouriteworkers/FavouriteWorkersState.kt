package com.fifty.workersportal.featurefavorites.presentation.favouriteworkers

import com.fifty.workersportal.featureworker.domain.model.Worker

data class FavouriteWorkersState(
    val favouriteWorkers: List<Worker> = emptyList(),
    val isLoading: Boolean = false
)
