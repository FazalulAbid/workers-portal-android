package com.fifty.workersportal.featureauth.util

import com.fifty.workersportal.featureauth.data.remote.response.VerifyOtpResponse

sealed class AuthResource<out T> {
    object Loading : AuthResource<Nothing>()

    data class Success<out T>(
        val data: T
    ) : AuthResource<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int
    ) : AuthResource<Nothing>()
}
