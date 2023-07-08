package com.fifty.workersportal.featureworker.presentation.searchworker

sealed class SearchWorkerEvent {
    data class Query(val query: String) : SearchWorkerEvent()
    object ToggleNearestSort : SearchWorkerEvent()
    object ToggleRatingFourPlusFilter : SearchWorkerEvent()
    object TogglePreviouslyHiredFilter : SearchWorkerEvent()
    object SelectRatingHighToLowSort : SearchWorkerEvent()
    object SelectDistanceLowToHighSort : SearchWorkerEvent()
    object SelectCostLowToHighSort : SearchWorkerEvent()
    object SelectCostHighToLowSort : SearchWorkerEvent()
    object ClickSortApply : SearchWorkerEvent()
    object ClickClearAllSortAndFilters : SearchWorkerEvent()
    object OnSheetDismiss: SearchWorkerEvent()
}
