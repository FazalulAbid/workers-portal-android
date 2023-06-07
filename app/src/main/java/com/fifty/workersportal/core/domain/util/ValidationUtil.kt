package com.fifty.workersportal.core.domain.util

import android.util.Log
import android.util.Patterns
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureworker.util.WorkerError

object ValidationUtil {

    fun validateEmail(email: String): WorkerError? {
        val trimmedEmail = email.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return WorkerError.InvalidEmail
        }
        if (trimmedEmail.isBlank()) {
            return WorkerError.FieldEmpty
        }
        return null
    }

    fun validateFirstName(firstName: String): WorkerError? {
        val trimmedUsername = firstName.trim()
        if (trimmedUsername.length < Constants.MINIMUM_NAME_LENGTH ||
            trimmedUsername.length > Constants.MAXIMUM_NAME_LENGTH
        ) {
            return WorkerError.InvalidFirstName
        }
        if (firstName.isBlank()) {
            return WorkerError.FieldEmpty
        }
        return null
    }

    fun validateWorkerAge(age: Int): WorkerError? {
        if (age < Constants.MINIMUM_WORKER_AGE || age > Constants.MAXIMUM_WORKER_AGE) {
            return WorkerError.InvalidAge
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

}