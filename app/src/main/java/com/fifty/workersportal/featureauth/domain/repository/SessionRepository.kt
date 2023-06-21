package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.domain.model.UserSession
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun getUserSession(): UserSession

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveUserSession(
        userId: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        isWorker: Boolean? = null
    )

    suspend fun deleteTokens()

    suspend fun deleteSessions()
}