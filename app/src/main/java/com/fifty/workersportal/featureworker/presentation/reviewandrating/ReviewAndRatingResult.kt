package com.fifty.workersportal.featureworker.presentation.reviewandrating

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError

data class ReviewAndRatingResult(
    val reviewError: ReviewAndRatingError? = null,
    val ratingError: ReviewAndRatingError? = null,
    val result: SimpleResource? = null
)
