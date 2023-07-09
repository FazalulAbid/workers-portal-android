package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class LogoutUseCase(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(): SimpleResource {
        sessionRepository.deleteUserId()
        sessionRepository.deleteTokens()
//        Session.userSession.value = null
        Session.selectedAddress.value = null
        return Resource.Success(Unit)
    }
}