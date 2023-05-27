package com.fifty.workersportal.core.util

import android.content.SharedPreferences

class TokenManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        const val KEY_JWT_ACCESS_TOKEN = "access_token"
        const val KEY_JWT_REFRESH_TOKEN = "refresh_token"
    }

    fun getAccessToken(): String? =
        sharedPreferences.getString(KEY_JWT_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? =
        sharedPreferences.getString(KEY_JWT_REFRESH_TOKEN, null)

    suspend fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit()
            .putString(KEY_JWT_ACCESS_TOKEN, accessToken)
            .apply()
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit()
            .putString(KEY_JWT_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    suspend fun deleteTokens() =
        sharedPreferences.edit()
            .remove(KEY_JWT_ACCESS_TOKEN)
            .remove(KEY_JWT_REFRESH_TOKEN)
            .apply()

}