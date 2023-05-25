package com.fifty.workersportal.featureworker.presentation.selectworkercategory

sealed class SelectWorkerCategoryEvent {
    data class Query(val query: String) : SelectWorkerCategoryEvent()
}
