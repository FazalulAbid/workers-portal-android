package com.fifty.workersportal.featureworker.presentation.reviewandrating

sealed class ReviewAndRatingEvent {
    data class RatingValueChange(val rating: Float) : ReviewAndRatingEvent()
    data class ReviewChanged(val review: String) : ReviewAndRatingEvent()
    object PostReviewAndRating : ReviewAndRatingEvent()
}