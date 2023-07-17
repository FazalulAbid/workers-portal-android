package com.fifty.fixitnow.core.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
    val data: T? = null,
    val uiText: UiText? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(uiText: UiText, data: T? = null, errorCode: Int? = null) :
        Resource<T>(data, uiText, errorCode)
}
