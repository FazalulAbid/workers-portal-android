package com.fifty.workersportal.featureworker.domain.model

data class ReviewAndRating(
    val userId: String,
    val rating: Float,
    val review: String,
    val isWorker: Boolean
)
