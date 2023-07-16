package com.fifty.workersportal.featureworker.data.remote.dto

import com.fifty.workersportal.featureworker.domain.model.RatingsCount
import com.google.gson.annotations.SerializedName

data class RatingCountDto(
    @SerializedName("_id")
    val workerId: String,
    @SerializedName("five")
    val excellent: Int,
    @SerializedName("four")
    val good: Int,
    @SerializedName("three")
    val average: Int,
    @SerializedName("two")
    val belowAverage: Int,
    @SerializedName("one")
    val poor: Int,
    val ratingAverage: Float,
    val ratingsCount: Int
) {
    fun toRatingsCount(): RatingsCount =
        RatingsCount(
            excellentPercentage = excellent.toFloat() / ratingsCount,
            goodPercentage = good.toFloat() / ratingsCount,
            averagePercentage = average.toFloat() / ratingsCount,
            belowAveragePercentage = belowAverage.toFloat() / ratingsCount,
            poorPercentage = poor.toFloat() / ratingsCount,
            ratingAverage = ratingAverage,
            ratingsCount = ratingsCount
        )
}
