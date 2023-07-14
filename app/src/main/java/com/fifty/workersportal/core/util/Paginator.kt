package com.fifty.workersportal.core.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}