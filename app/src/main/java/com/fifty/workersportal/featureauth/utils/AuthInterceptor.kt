package com.fifty.workersportal.featureauth.utils

import android.util.Log
import com.fifty.workersportal.core.data.util.ApiConstants.AUTHORIZATION_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    private val TAG = "Hello AuthInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            Log.d(TAG, "intercept: Inside run blocking")
            tokenManager.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader(
            AUTHORIZATION_KEY,
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NDc4YWJhNWExYTRjNmY1ZDllNzJiNjIiLCJpYXQiOjE2ODU2OTMzNjIsImV4cCI6MTY4NTY5NTE2Mn0.Z-GZJOG2cAo_a1J15Y_-TexbtdlQENVCMTOURBkQqaY"
        )
        return chain.proceed(request.build())
    }
}