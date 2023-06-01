package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.data.remote.request.SendOtpRequest
import com.fifty.workersportal.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.workersportal.featureauth.data.remote.response.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("send-sms-otp")
    suspend fun getOtp(
        @Body request: SendOtpRequest
    ): BasicApiResponse<Unit>

    @POST("verify-sms-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<BasicApiResponse<VerifyOtpResponse>>

    @GET("refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<Unit>

    companion object {
        const val BASE_URL = Constants.WORKERS_PORTAL_BASE_URL
    }
}