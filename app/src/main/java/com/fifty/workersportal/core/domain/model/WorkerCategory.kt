package com.fifty.workersportal.core.domain.model

data class WorkerCategory(
    val id: String,
    val title: String,
    val minimumWage: Float,
    val imageUrl: String
)