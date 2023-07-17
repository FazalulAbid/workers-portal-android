package com.fifty.fixitnow.featureuser.presentation.userdashboard

import com.fifty.fixitnow.featureworker.domain.model.Category

data class SuggestedCategoriesState(
    val suggestedCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
