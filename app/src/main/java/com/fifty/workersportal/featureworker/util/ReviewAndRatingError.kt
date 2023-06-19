package com.fifty.workersportal.featureworker.util

sealed class ReviewAndRatingError : Error() {
    object EmptyField : ReviewAndRatingError()
    object RatingError : ReviewAndRatingError()
}
