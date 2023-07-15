package com.fifty.workersportal.featurefavorites.presentation.favouriteworkers

sealed class FavouriteWorkersEvent {
    data class ToggleFavouriteWorkers(val workerId: String) : FavouriteWorkersEvent()
    object LoadFavouriteWorkers : FavouriteWorkersEvent()
}
