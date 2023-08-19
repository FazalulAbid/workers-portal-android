package com.fifty.fixitnow.featureauth.utils

import com.fifty.fixitnow.core.data.util.ApiConstants.AUTHORIZATION_KEY
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
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