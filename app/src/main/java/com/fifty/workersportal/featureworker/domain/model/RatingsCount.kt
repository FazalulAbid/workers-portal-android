package com.fifty.workersportal.featureworker.domain.model

data class RatingsCount(
    val excellentPercentage: Float,
    val goodPercentage: Float,
    val averagePercentage: Float,
    val belowAveragePercentage: Float,
    val poorPercentage: Float,
    val ratingAverage: Float?,
    val ratingsCount: Int
)
