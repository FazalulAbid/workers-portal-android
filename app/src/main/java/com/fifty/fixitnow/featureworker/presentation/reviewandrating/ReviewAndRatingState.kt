package com.fifty.fixitnow.featureworker.presentation.reviewandrating

import com.fifty.fixitnow.featureworker.domain.model.RatingsCount

data class ReviewAndRatingState(
    val isLoading: Boolean = false,
    val workerRatingsCount: RatingsCount? = null
)
