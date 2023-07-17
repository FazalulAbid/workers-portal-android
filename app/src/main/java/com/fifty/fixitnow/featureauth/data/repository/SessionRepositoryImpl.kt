package com.fifty.fixitnow.featureauth.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.fifty.fixitnow.core.util.dataStore
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import com.fifty.fixitnow.featureauth.utils.AuthConstants.KEY_JWT_ACCESS_TOKEN
import com.fifty.fixitnow.featureauth.utils.AuthConstants.KEY_JWT_REFRESH_TOKEN
import com.fifty.fixitnow.featureauth.utils.AuthConstants.KEY_USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepositoryImpl(
    private val context: Context
) : SessionRepository {

    override fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_JWT_ACCESS_TOKEN]
        }
    }

    override fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_JWT_REFRESH_TOKEN]
        }
    }

    override fun getOwnUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_USER_ID]
        }
    }

    override suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    override suspend fun deleteUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_JWT_ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_JWT_REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun deleteTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_JWT_ACCESS_TOKEN)
            preferences.remove(KEY_JWT_REFRESH_TOKEN)
        }
    }
}