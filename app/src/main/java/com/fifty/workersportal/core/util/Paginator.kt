package com.fifty.workersportal.core.util

interface Paginator {
    suspend fun loadNextItems()
    fun resetPagination()
}