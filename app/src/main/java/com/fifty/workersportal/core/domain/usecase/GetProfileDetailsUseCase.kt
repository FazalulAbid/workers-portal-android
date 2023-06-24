package com.fifty.workersportal.core.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository

class GetProfileDetailsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfileDetails(userId = userId)
    }
}