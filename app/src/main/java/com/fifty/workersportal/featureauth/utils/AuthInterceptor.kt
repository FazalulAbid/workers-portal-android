package com.fifty.workersportal.featureauth.utils

import com.fifty.workersportal.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionRepository: SessionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            sessionRepository.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader(
            AUTHORIZATION_KEY,
            "$accessToken"
        )
        return chain.proceed(request.build())
    }
}