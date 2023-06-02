package com.fifty.workersportal.featureauth.utils

import android.util.Log
import com.fifty.workersportal.core.data.util.ApiConstants.ACCESS_TOKEN_KEY
import com.fifty.workersportal.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
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
    private val tokenManager: TokenManager
) : Authenticator {

    private val TAG = "Hello AuthAuthenticator"

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "authenticate: Authenticator Worked")
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }
        return runBlocking {
            val newAccessTokenResponse = getNewAccessToken(refreshToken)
            Log.d(TAG, "authenticate: ${newAccessTokenResponse.headers()}")
            val newAccessToken = newAccessTokenResponse.headers().let {
                it[ACCESS_TOKEN_KEY]
            }

            if (!newAccessTokenResponse.isSuccessful || newAccessToken.isNullOrBlank()) {
                tokenManager.deleteTokens()
            }

            newAccessToken?.let {
                tokenManager.saveAccessToken(it)
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