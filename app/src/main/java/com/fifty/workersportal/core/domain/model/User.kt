package com.fifty.workersportal.core.domain.model

data class User(
    val id: String,
    val firstName: String,
    val isWorker: Boolean,
    val lastName: String,
)
