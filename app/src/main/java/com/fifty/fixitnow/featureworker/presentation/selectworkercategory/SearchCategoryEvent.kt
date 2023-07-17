package com.fifty.fixitnow.featureworker.presentation.selectworkercategory

import com.fifty.fixitnow.featureworker.domain.model.Category

sealed class SearchCategoryEvent {
    data class SelectCategory(val category: Category) : SearchCategoryEvent()
    data class Query(val query: String) : SearchCategoryEvent()
}
