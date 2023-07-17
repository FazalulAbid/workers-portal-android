package com.fifty.fixitnow.featureworker.presentation.reviewandrating

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.util.ReviewAndRatingError

data class ReviewAndRatingResult(
    val reviewError: ReviewAndRatingError? = null,
    val ratingError: ReviewAndRatingError? = null,
    val result: SimpleResource? = null
)
