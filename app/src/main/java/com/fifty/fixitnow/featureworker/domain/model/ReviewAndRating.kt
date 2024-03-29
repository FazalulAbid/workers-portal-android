package com.fifty.fixitnow.featureworker.domain.model

data class ReviewAndRating(
    val id: String,
    val profileImageUrl: String,
    val firstName: String,
    val lastName: String,
    val userId: String,
    val ratedUserId: String,
    val rating: Float,
    val review: String,
    val formattedTime: String,
    val isWorker: Boolean
)