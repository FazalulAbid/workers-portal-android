package com.fifty.fixitnow.featurefavorites.presentation.favouriteworkers

sealed class FavouriteWorkersEvent {
    data class ToggleFavouriteWorkers(val workerId: String) : FavouriteWorkersEvent()
    object LoadFavouriteWorkers : FavouriteWorkersEvent()
}
