package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository
import com.fifty.fixitnow.featureuser.domain.model.UserProfile

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