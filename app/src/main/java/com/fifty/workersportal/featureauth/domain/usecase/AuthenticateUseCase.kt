package com.fifty.workersportal.featureauth.domain.usecase

import android.util.Log
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository

class AuthenticateUseCase(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(): SimpleResource {
        return when (val result = authRepository.authenticate()) {
            is Resource.Success -> {
                result.data?.let {
                    Log.d("Hello", "invoke: $it")
                    sessionRepository.saveUserSession(it)
                    Resource.Success(Unit)
                } ?: Resource.Error(UiText.unknownError())
            }

            is Resource.Error -> {
                Resource.Error(UiText.unknownError())
            }
        }
    }
}