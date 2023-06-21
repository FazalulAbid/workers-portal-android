package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class SaveUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(
        userId: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        isWorker: Boolean? = null
    ) {
        sessionRepository.saveUserSession(
            userId = userId,
            firstName = firstName,
            lastName = lastName,
            isWorker = isWorker
        )
    }
}