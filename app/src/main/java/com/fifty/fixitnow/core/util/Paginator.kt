package com.fifty.fixitnow.core.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}