package com.fifty.fixitnow.core.presentation

import com.fifty.fixitnow.core.util.UiText

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: UiText? = null,
    val page: Int = 0
)