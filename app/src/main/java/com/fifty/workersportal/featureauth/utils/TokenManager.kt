package com.fifty.workersportal.featureauth.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {
    companion object {
        private val KEY_JWT_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_JWT_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val KEY_USER_ID = stringPreferencesKey(Constants.KEY_USER_ID)
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_JWT_ACCESS_TOKEN]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_JWT_REFRESH_TOKEN]
        }
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_JWT_ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_JWT_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun deleteTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_JWT_ACCESS_TOKEN)
            preferences.remove(KEY_JWT_REFRESH_TOKEN)
        }
    }

}