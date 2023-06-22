package com.fifty.workersportal.core.domain.usecase

import android.util.Log
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState

class GetUserProfileDetailsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: String,
        needSampleWorks: Boolean = false
    ): Resource<Profile> {
        val result = repository.getUserProfileDetails(
            userId = userId,
            needSampleWorks = needSampleWorks
        )
        Log.d("Hello", "GetUserProfileDetailsUseCase: ${result.data}")
        return result
    }
}