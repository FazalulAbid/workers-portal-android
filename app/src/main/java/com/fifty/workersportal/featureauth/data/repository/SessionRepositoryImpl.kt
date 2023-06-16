package com.fifty.workersportal.featureauth.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.util.dataStore
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_JWT_ACCESS_TOKEN
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_JWT_REFRESH_TOKEN
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_USER_FIRST_NAME
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_USER_ID
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_USER_IS_WORKER
import com.fifty.workersportal.featureauth.utils.AuthConstants.KEY_USER_LAST_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    override suspend fun getUserSession(): UserSession {
        val preferences = context.dataStore.data.first()
        val userSession = UserSession(
            id = preferences[KEY_USER_ID] ?: "",
            firstName = preferences[KEY_USER_FIRST_NAME] ?: "",
            lastName = preferences[KEY_USER_LAST_NAME] ?: "",
            isWorker = preferences[KEY_USER_IS_WORKER] ?: false
        )
        Session.userId = userSession.id
        Session.firstName = userSession.firstName
        Session.lastName = userSession.lastName
        Session.isWorker = userSession.isWorker
        return userSession
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

    override suspend fun saveUserSession(userSession: UserSession) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userSession.id
            preferences[KEY_USER_FIRST_NAME] = userSession.firstName
            preferences[KEY_USER_LAST_NAME] = userSession.lastName
            preferences[KEY_USER_IS_WORKER] = userSession.isWorker
        }
        Session.userId = userSession.id
        Session.firstName = userSession.firstName
        Session.lastName = userSession.lastName
        Session.isWorker = userSession.isWorker
        Log.d("Hello", "saveUserSession: Saved new one ${Session.isWorker}")
    }

    override suspend fun deleteTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_JWT_ACCESS_TOKEN)
            preferences.remove(KEY_JWT_REFRESH_TOKEN)
        }
    }

    override suspend fun deleteSessions() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_FIRST_NAME)
            preferences.remove(KEY_USER_LAST_NAME)
            preferences.remove(KEY_USER_IS_WORKER)
        }
    }
}