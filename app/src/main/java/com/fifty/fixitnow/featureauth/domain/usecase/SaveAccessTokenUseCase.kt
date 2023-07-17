package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository

class SaveAccessTokenUseCase(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(accessToken: String) =
        repository.saveAccessToken(accessToken)

}