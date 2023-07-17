package com.fifty.fixitnow.featureauth.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    fun getOwnUserId(): Flow<String?>

    suspend fun saveUserId(userId: String)

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun deleteTokens()

    suspend fun deleteUserId()
}