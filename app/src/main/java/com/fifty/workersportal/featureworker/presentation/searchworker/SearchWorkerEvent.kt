package com.fifty.workersportal.featureworker.presentation.searchworker

sealed class SearchWorkerEvent {
    data class Query(val query: String) : SearchWorkerEvent()
}
