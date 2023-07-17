package com.fifty.fixitnow.featureworker.presentation.reviewandrating

sealed class ReviewAndRatingEvent {
    data class RatingValueChange(val rating: Int) : ReviewAndRatingEvent()
    data class ReviewChanged(val review: String) : ReviewAndRatingEvent()
    object PostReviewAndRating : ReviewAndRatingEvent()
}
