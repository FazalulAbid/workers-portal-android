package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.featureuser.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    fun getUserId(): Flow<String?>

    suspend fun saveUserId(userId: String)

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun deleteTokens()

    suspend fun deleteUserId()
}