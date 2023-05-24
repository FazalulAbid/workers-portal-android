package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureauth.data.remote.request.AuthRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("user/sent-otp")
    suspend fun getOtp(
        @Body request: AuthRequest
    ): BasicApiResponse<Unit>
}