package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun getUserSession(): User

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveUserSession(user: User)

    suspend fun deleteTokens()

    suspend fun deleteSessions()
}