package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class SaveRefreshTokenUseCase(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(refreshToken: String) =
        repository.saveRefreshToken(refreshToken)
}