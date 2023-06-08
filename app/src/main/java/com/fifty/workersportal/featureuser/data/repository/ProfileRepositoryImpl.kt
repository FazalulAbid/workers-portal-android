package com.fifty.workersportal.featureuser.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureuser.data.remote.ProfileApiService
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApiService
) : ProfileRepository {

    override suspend fun getUserProfileDetails(userId: String): Resource<Profile> {
        return try {
            Log.d("Hello", "getUserProfileDetails: Before $userId")
            val response = api.getUserProfileDetails(userId = userId)
            Log.d("Hello", "getUserProfileDetails: After")
            if (response.successful) {
                Resource.Success(data = response.data?.toProfile())
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
        updateProfileForWorkerRequest: UpdateProfileForWorkerRequest
    ): Resource<Profile> {
        return try {
            Log.d("Hello", "updateProfileForWorker: $updateProfileForWorkerRequest")
            val response = api.updateProfileForWorker(updateProfileForWorkerRequest)
            Log.d("Hello", "response: $response")
            if (response.successful) {
                Resource.Success(data = response.data?.toProfile())
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