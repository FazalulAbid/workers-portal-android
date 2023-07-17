package com.fifty.fixitnow.featureworker.data.remote.request

data class ReviewAndRatingRequest(
    val review: String,
    val rating: Float,
    val ratedUserId: String,
    val isWorker: Boolean
)
