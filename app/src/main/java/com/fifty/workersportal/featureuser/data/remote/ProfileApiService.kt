package com.fifty.workersportal.featureuser.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApiService {

    @GET("profile/get-user-details")
    suspend fun getUserProfileDetails(
        @Query("id") userId: String
    ): BasicApiResponse<ProfileDto>

    @Multipart
    @PUT("profile/register-as-worker")
    suspend fun updateProfileForWorker(
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileForWorkerRequest: MultipartBody.Part
    ): BasicApiResponse<ProfileDto>
}