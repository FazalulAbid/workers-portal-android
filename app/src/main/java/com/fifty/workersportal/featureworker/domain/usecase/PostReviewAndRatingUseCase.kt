package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.domain.util.ValidationUtil.validateRating
import com.fifty.workersportal.core.domain.util.ValidationUtil.validateReview
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import com.fifty.workersportal.featureworker.presentation.reviewandrating.ReviewAndRatingResult
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError

class PostReviewAndRatingUseCase(
    private val repository: ReviewAndRatingRepository
) {

    suspend operator fun invoke(
        ratedUserId: String,
        rating: Float,
        review: String,
        isWorker: Boolean
    ): ReviewAndRatingResult {
        val reviewError = validateReview(review)
        val ratingError = validateRating(rating)

        return if (reviewError != null || ratingError != null) {
            ReviewAndRatingResult(
                reviewError = reviewError,
                ratingError = ratingError
            )
        } else {
            ReviewAndRatingResult(
                result = repository.postReviewAndRating(
                    ratedUserId = ratedUserId,
                    review = review,
                    rating = rating,
                    isWorker = isWorker
                )
            )
        }
    }
}