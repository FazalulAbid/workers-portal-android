package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import com.fifty.workersportal.featureworker.presentation.reviewandrating.ReviewAndRatingResult
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError

class PostReviewAndRatingUseCase(
    private val repository: ReviewAndRatingRepository
) {

    suspend operator fun invoke(reviewAndRating: ReviewAndRating): ReviewAndRatingResult {
        val reviewError = validateReview(reviewAndRating.review)
        val ratingError = validateRating(reviewAndRating.rating)

        return if (reviewError != null || ratingError != null) {
            ReviewAndRatingResult(
                reviewError = reviewError,
                ratingError = ratingError
            )
        } else {
            ReviewAndRatingResult(
                result = repository.postReviewAndRating(reviewAndRating)
            )
        }
    }

    private fun validateReview(review: String): ReviewAndRatingError? {
        if (review.trim().isBlank()) {
            return ReviewAndRatingError.EmptyField
        }
        return null
    }

    private fun validateRating(rating: Float): ReviewAndRatingError? {
        if (rating <= 0) {
            return ReviewAndRatingError.RatingError
        }
        return null
    }
}