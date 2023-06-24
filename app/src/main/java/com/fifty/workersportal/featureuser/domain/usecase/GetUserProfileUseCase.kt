package com.fifty.workersportal.featureuser.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.UserProfile
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository

class GetUserProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: String
    ): Resource<UserProfile> {

        return repository.getUserProfile(userId = userId)
    }
}