package com.fifty.workersportal.featureworker.presentation.selectworkercategory

sealed class SearchCategoryEvent {
    data class Query(val query: String) : SearchCategoryEvent()
}
