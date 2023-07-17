package com.fifty.fixitnow.featureauth.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.featureuser.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.GET

interface AuthenticateApiService {

    @GET("auth/authenticate")
    suspend fun authenticate(): Response<BasicApiResponse<ProfileDto>>
}