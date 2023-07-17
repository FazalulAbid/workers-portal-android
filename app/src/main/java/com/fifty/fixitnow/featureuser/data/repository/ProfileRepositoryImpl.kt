package com.fifty.fixitnow.featureuser.data.repository

import android.net.Uri
import androidx.core.net.toFile
import coil.network.HttpException
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureuser.data.remote.ProfileApiService
import com.fifty.fixitnow.featureuser.domain.model.Profile
import com.fifty.fixitnow.featureuser.domain.model.UpdateUserProfileData
import com.fifty.fixitnow.featureuser.domain.model.UserProfile
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository
import com.fifty.fixitnow.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApiService,
    private val gson: Gson
) : ProfileRepository {

    override suspend fun getProfileDetails(userId: String): Resource<Profile> {
        return try {
            val response = api.getUserProfileDetails(userId = userId)
            if (response.successful) {
                val profile = response.data?.toProfile()
                if (profile?.id == Session.userSession.value?.id) {
                    Session.userSession.value = profile?.toUserProfile()
                }
                Resource.Success(data = profile)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }

    override suspend fun getUserProfile(userId: String): Resource<UserProfile> {
        return try {
            val response = api.getUserProfileDetails(userId = userId)
            if (response.successful) {
                val userProfile = response.data?.toProfile()?.toUserProfile()
                if (userProfile?.id == Session.userSession.value?.id) {
                    Session.userSession.value = userProfile
                }
                Resource.Success(data = userProfile)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }

    override suspend fun updateUserProfile(
        updateUserProfileData: UpdateUserProfileData,
        profilePictureUri: Uri?
    ): Resource<UserProfile> {
        val profilePictureFile = profilePictureUri?.toFile()
        return try {
            val response = api.updateUserProfile(
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profilePicture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                updateUserProfile = MultipartBody.Part
                    .createFormData(
                        "data",
                        gson.toJson(updateUserProfileData)
                    )
            )
            if (response.successful) {
                val userProfile = response.data?.toProfile()?.toUserProfile()
                Session.userSession.value = userProfile
                Resource.Success(data = userProfile)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }

    override suspend fun updateProfileForWorker(
        updateProfileForWorkerRequest: UpdateProfileForWorkerRequest,
        profilePictureUri: Uri?,
        identityPictureUri: Uri?
    ): Resource<Profile> {
        val profilePictureFile = profilePictureUri?.toFile()
        val identityPictureFile = identityPictureUri?.toFile()

        return try {
            val response = api.updateProfileForWorker(
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profilePicture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                identityPicture = identityPictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "identity",
                            identityPictureFile.name,
                            identityPictureFile.asRequestBody()
                        )
                },
                updateProfileForWorkerRequest = MultipartBody.Part
                    .createFormData(
                        "data",
                        gson.toJson(updateProfileForWorkerRequest)
                    )
            )
            if (response.successful) {
                val profile = response.data?.toProfile()
                Session.userSession.value = profile?.toUserProfile()
                Resource.Success(data = profile)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }
}