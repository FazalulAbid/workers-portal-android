package com.fifty.workersportal.core.domain.util

import android.util.Patterns
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureworker.util.ProfileError
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError

object ValidationUtil {

    fun validateEmail(email: String): ProfileError? {
        val trimmedEmail = email.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ProfileError.InvalidEmail
        }
        if (trimmedEmail.isBlank()) {
            return ProfileError.FieldEmpty
        }
        return null
    }

    fun validateFirstName(firstName: String): ProfileError? {
        val trimmedUsername = firstName.trim()
        if (trimmedUsername.length < Constants.MINIMUM_NAME_LENGTH ||
            trimmedUsername.length > Constants.MAXIMUM_NAME_LENGTH
        ) {
            return ProfileError.InvalidFirstName
        }
        if (firstName.isBlank()) {
            return ProfileError.FieldEmpty
        }
        return null
    }

    fun validateWorkerAge(age: Int): ProfileError? {
        if (age < Constants.MINIMUM_WORKER_AGE || age > Constants.MAXIMUM_WORKER_AGE) {
            return ProfileError.InvalidAge
        }
        return null
    }

    fun validateWage(wage: String, minWage: Float): Float {
        var validatedInput = wage

        if (validatedInput.endsWith('.')) {
            validatedInput += '0'
        }

        return try {
            val parsedValue = validatedInput.toFloat()
            if (parsedValue < minWage) {
                minWage
            } else {
                parsedValue
            }
        } catch (e: NumberFormatException) {
            minWage
        }
    }

    fun validateReview(review: String): ReviewAndRatingError? {
        if (review.trim().isBlank()) {
            return ReviewAndRatingError.EmptyField
        }
        return null
    }

    fun validateRating(rating: Float): ReviewAndRatingError? {
        if (rating <= 0) {
            return ReviewAndRatingError.RatingError
        }
        return null
    }

}