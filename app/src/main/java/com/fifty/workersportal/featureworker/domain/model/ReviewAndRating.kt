package com.fifty.workersportal.featureworker.domain.model

data class ReviewAndRating(
    val id: String,
    val firstName: String,
    val lastName: String,
    val userId: String,
    val ratedUserId: String,
    val rating: Float,
    val review: String,
    val formattedTime: String,
    val isWorker: Boolean
)