package com.fifty.workersportal.core.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class GetUserIsWorkerUseCase(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(): Boolean {
        val user = repository.getUserSession()
        return user.isWorker
    }
}