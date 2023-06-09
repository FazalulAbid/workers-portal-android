package com.fifty.workersportal.featureuser.domain.repository

import android.net.Uri
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureuser.domain.model.UpdateUserProfileData
import com.fifty.workersportal.featureuser.domain.model.UserProfile
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest

interface ProfileRepository {

    suspend fun getProfileDetails(userId: String): Resource<Profile>

    suspend fun getUserProfile(userId: String): Resource<UserProfile>

    suspend fun updateUserProfile(
        updateUserProfileData: UpdateUserProfileData,
        profilePictureUri: Uri?
    ): Resource<UserProfile>

    suspend fun updateProfileForWorker(
        updateProfileForWorkerRequest: UpdateProfileForWorkerRequest,
        profilePictureUri: Uri?,
        identityPictureUri: Uri?
    ): Resource<Profile>
}