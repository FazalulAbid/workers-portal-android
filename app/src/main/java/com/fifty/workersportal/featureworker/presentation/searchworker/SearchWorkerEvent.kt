package com.fifty.workersportal.featureworker.presentation.searchworker

sealed class SearchWorkerEvent {
    data class Query(val query: String) : SearchWorkerEvent()
    data class AddToFavourite(val workerId: String) : SearchWorkerEvent()
    object ToggleNearestSort : SearchWorkerEvent()
    object ToggleRatingFourPlusFilter : SearchWorkerEvent()
    object TogglePreviouslyHiredFilter : SearchWorkerEvent()
    object SelectRelevance : SearchWorkerEvent()
    object SelectRatingHighToLowSort : SearchWorkerEvent()
    object SelectDistanceLowToHighSort : SearchWorkerEvent()
    object SelectCostLowToHighSort : SearchWorkerEvent()
    object SelectCostHighToLowSort : SearchWorkerEvent()
    object ApplySort : SearchWorkerEvent()
    object ClearAllSortAndFilters : SearchWorkerEvent()
    object OnSheetDismiss : SearchWorkerEvent()
}
