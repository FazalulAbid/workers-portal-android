package com.fifty.fixitnow.featureauth.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.fixitnow.featureauth.data.remote.request.SendOtpRequest
import com.fifty.fixitnow.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.fixitnow.featureuser.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    @POST("auth/google-auth")
    suspend fun googleSignIn(
        @Query("googleToken") googleToken: String
    ): Response<BasicApiResponse<ProfileDto>>
}