package com.fifty.workersportal.featureuser.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest

interface ProfileRepository {

    suspend fun getUserProfileDetails(userId: String): Resource<Profile>

    suspend fun updateProfileForWorker(
        updateProfileForWorkerRequest: UpdateProfileForWorkerRequest
    ): Resource<Profile>
}