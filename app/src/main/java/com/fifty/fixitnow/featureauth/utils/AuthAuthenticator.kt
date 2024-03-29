package com.fifty.fixitnow.featureauth.utils

import com.fifty.fixitnow.core.data.util.ApiConstants.ACCESS_TOKEN_KEY
import com.fifty.fixitnow.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featureauth.data.remote.AuthApiService
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sessionRepository: SessionRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            sessionRepository.getRefreshToken().first()
        }
        return runBlocking {
            val newAccessTokenResponse = getNewAccessToken(refreshToken)
            val newAccessToken = newAccessTokenResponse.headers().let {
                it[ACCESS_TOKEN_KEY]
            }

            if (!newAccessTokenResponse.isSuccessful || newAccessToken.isNullOrBlank()) {
                sessionRepository.deleteTokens()
            }

            newAccessToken?.let {
                sessionRepository.saveAccessToken(it)
                response.request.newBuilder()
                    .header(AUTHORIZATION_KEY, it)
                    .build()
            }
        }
    }

    private suspend fun getNewAccessToken(refreshToken: String?): retrofit2.Response<Unit> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.WORKERS_PORTAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApiService::class.java)
        return service.refreshToken(refreshToken ?: "")
    }
}