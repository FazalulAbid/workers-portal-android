package com.fifty.workersportal.core.util

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getRefreshToken()
        }
//        return runBlocking {
//            val newToken = getNewToken(token)
//
//            if (!newToken.successful || newToken.data == null) {
//                tokenManager.deleteTokens()
//            }
//
//            newToken.data.let {
//
//            }
//        }
        return null
    }

    private suspend fun getNewToken(refreshToken: String?): BasicApiResponse<Unit> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.WORKERS_PORTAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApiService::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}