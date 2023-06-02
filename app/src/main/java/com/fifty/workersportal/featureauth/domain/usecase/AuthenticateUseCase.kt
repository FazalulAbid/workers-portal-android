package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}