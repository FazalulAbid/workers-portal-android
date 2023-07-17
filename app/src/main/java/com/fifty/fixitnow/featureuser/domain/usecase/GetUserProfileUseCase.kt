package com.fifty.fixitnow.featureuser.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureuser.domain.model.UserProfile
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository

class GetUserProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: String
    ): Resource<UserProfile> {

        return repository.getUserProfile(userId = userId)
    }
}