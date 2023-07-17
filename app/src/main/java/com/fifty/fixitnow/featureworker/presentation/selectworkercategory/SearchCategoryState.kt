package com.fifty.fixitnow.featureworker.presentation.selectworkercategory

import com.fifty.fixitnow.featureworker.domain.model.Category

data class SearchCategoryState(
    val workerCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
