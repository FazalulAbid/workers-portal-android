package com.fifty.workersportal.core.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class GetOwnUserIdUseCase(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(): String {
        val user = repository.getUserSession()
        return user.userId
    }
}