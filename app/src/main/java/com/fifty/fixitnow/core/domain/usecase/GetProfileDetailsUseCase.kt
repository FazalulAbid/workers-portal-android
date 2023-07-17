package com.fifty.fixitnow.core.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureuser.domain.model.Profile
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository

class GetProfileDetailsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfileDetails(userId = userId)
    }
}