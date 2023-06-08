package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

data class SearchCategoryState(
    val workerCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
