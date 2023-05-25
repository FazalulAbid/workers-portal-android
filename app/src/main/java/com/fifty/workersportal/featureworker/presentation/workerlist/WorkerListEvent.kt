package com.fifty.workersportal.featureworker.presentation.workerlist

sealed class WorkerListEvent {
    data class Query(val query: String) : WorkerListEvent()
}
