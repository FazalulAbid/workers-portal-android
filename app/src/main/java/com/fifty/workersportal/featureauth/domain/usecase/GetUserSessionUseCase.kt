package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class GetUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): UserSession =
        sessionRepository.getUserSession()
}