package com.fifty.workersportal.featureworker.domain.repository

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating

interface ReviewAndRatingRepository {

    suspend fun postReviewAndRating(reviewAndRating: ReviewAndRating): SimpleResource
}