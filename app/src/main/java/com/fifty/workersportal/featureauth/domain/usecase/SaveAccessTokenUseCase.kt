package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class SaveAccessTokenUseCase(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(accessToken: String) =
        repository.saveAccessToken(accessToken)

}