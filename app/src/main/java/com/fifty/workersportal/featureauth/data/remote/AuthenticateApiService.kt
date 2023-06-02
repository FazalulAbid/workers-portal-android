package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureauth.data.remote.response.UserResponse
import retrofit2.http.GET

interface AuthenticateApiService {

    @GET("authenticate")
    suspend fun authenticate(): BasicApiResponse<UserResponse>
}