package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.data.remote.request.AuthRequest
import com.fifty.workersportal.featureauth.data.remote.response.VerifyOtpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("/user/sent-otp")
    suspend fun getOtp(
        @Body request: AuthRequest
    ): BasicApiResponse<Unit>

    @POST("/user/signup-and-login")
    suspend fun verifyOtp(
        @Body request: AuthRequest
    ): BasicApiResponse<VerifyOtpResponse>

    @GET("refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): BasicApiResponse<Unit>

    companion object {
        const val BASE_URL = Constants.WORKERS_PORTAL_BASE_URL
    }
}