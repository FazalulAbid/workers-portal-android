package com.fifty.workersportal.featureworker.domain.model

data class ReviewAndRating(
    val id: String,
    val ratingUsername: String,
    val userId: String,
    val ratedUserId: String,
    val rating: Float,
    val review: String,
    val isWorker: Boolean
)