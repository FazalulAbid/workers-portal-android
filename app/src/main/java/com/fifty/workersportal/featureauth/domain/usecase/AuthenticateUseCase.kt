package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureuser.domain.model.UserProfile

class AuthenticateUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Resource<UserProfile> {
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