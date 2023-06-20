package com.fifty.workersportal.featureworker.domain.model

data class ReviewAndRating(
    val ratedUserId: String,
    val rating: Float,
    val review: String,
    val isWorker: Boolean
)
