package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import com.fifty.workersportal.featureworker.domain.model.Category

sealed class SearchCategoryEvent {
    data class SelectCategory(val category: Category) : SearchCategoryEvent()
    data class Query(val query: String) : SearchCategoryEvent()
}
