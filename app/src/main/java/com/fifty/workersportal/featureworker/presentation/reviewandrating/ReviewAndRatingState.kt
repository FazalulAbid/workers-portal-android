package com.fifty.workersportal.featureworker.presentation.reviewandrating

import com.fifty.workersportal.featureworker.domain.model.RatingsCount

data class ReviewAndRatingState(
    val isLoading: Boolean = false,
    val workerRatingsCount: RatingsCount? = null
)
