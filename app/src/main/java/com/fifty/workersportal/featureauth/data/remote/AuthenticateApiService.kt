package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.GET

interface AuthenticateApiService {

    @GET("auth/authenticate")
    suspend fun authenticate(): Response<BasicApiResponse<ProfileDto>>
}