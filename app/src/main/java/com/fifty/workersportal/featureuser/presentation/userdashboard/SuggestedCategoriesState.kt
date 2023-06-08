package com.fifty.workersportal.featureuser.presentation.userdashboard

import com.fifty.workersportal.featureworker.domain.model.Category

data class SuggestedCategoriesState(
    val suggestedCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
