package com.fifty.workersportal.featureauth.domain.usecase

import android.util.Log
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class AuthenticateUseCase(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(): Resource<UserSession> {
        return when (val result = authRepository.authenticate()) {
            is Resource.Success -> {
                result.data?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(UiText.unknownError())
            }

            is Resource.Error -> {
                Resource.Error(UiText.unknownError())
            }
        }
    }
}