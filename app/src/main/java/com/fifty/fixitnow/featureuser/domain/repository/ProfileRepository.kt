package com.fifty.fixitnow.featureuser.domain.repository

import android.net.Uri
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureuser.domain.model.Profile
import com.fifty.fixitnow.featureuser.domain.model.UpdateUserProfileData
import com.fifty.fixitnow.featureuser.domain.model.UserProfile
import com.fifty.fixitnow.featureworker.data.remote.request.UpdateProfileForWorkerRequest

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