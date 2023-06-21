package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class SaveUserIdUseCase(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(userId: String) {
        sessionRepository.saveUserId(userId = userId)
    }
}