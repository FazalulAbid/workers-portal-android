package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class GetAccessTokenUseCase(
    private val sessionRepository: SessionRepository
) {

    operator fun invoke(): Flow<String?> = sessionRepository.getAccessToken()
}