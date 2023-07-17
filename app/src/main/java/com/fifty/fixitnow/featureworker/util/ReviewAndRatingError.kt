package com.fifty.fixitnow.featureworker.util

sealed class ReviewAndRatingError : Error() {
    object EmptyField : ReviewAndRatingError()
    object RatingError : ReviewAndRatingError()
}
