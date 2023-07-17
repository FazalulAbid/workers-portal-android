package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository

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