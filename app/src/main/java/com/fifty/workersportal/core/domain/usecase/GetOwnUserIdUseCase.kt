package com.fifty.workersportal.core.domain.usecase

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetOwnUserIdUseCase(
    private val context: Context
) {
    suspend operator fun invoke(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(Constants.KEY_USER_ID)]
        }.first()
    }
}