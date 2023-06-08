package com.fifty.workersportal.core.domain.model

data class UserSession(
    val id: String,
    val firstName: String,
    val isWorker: Boolean,
    val lastName: String,
)
