package com.fifty.workersportal.featureworker.data.remote.dto

import com.fifty.workersportal.core.util.toDaysAgo
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.google.gson.annotations.SerializedName

data class ReviewAndRatingDto(
    @SerializedName("_id")
    val id: String,
    val userId: String,
    val ratedUserId: String,
    val rating: Float,
    val review: String,
    val isWorker: Boolean,
    val timestamp: Long,
    val firstName: String,
    val lastName: String,
    val profileImageUrl: String
) {
    fun toReviewAndRating(): ReviewAndRating {
        return ReviewAndRating(
            id = id,
            firstName = firstName,
            lastName = lastName,
            profileImageUrl = profileImageUrl,
            userId = userId,
            ratedUserId = ratedUserId,
            rating = rating,
            review = review,
            isWorker = isWorker,
            formattedTime = timestamp.toDaysAgo()
        )
    }
}
