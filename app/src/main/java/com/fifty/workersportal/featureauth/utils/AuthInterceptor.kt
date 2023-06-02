package com.fifty.workersportal.featureauth.utils

import com.fifty.workersportal.core.data.util.ApiConstants.AUTHORIZATION_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            sessionManager.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader(
            AUTHORIZATION_KEY,
            "$accessToken"
        )
        return chain.proceed(request.build())
    }
}