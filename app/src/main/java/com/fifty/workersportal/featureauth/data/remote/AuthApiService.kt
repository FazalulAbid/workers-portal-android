package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.workersportal.featureauth.data.remote.request.SendOtpRequest
import com.fifty.workersportal.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/send-sms-otp")
    suspend fun getOtp(
        @Body request: SendOtpRequest
    ): BasicApiResponse<String>

    @POST("auth/verify-sms-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<BasicApiResponse<ProfileDto>>

    @GET("auth/refresh-token")
    suspend fun refreshToken(
        @Header(AUTHORIZATION_KEY) token: String
    ): Response<Unit>
}