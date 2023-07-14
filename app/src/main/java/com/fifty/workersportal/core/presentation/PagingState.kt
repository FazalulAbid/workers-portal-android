package com.fifty.workersportal.core.presentation

import com.fifty.workersportal.core.util.UiText

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: UiText? = null,
    val page: Int = 0
)