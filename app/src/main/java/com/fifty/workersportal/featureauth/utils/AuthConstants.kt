package com.fifty.workersportal.featureauth.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AuthConstants {

    val KEY_JWT_ACCESS_TOKEN = stringPreferencesKey("accessToken")
    val KEY_JWT_REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    val KEY_USER_ID = stringPreferencesKey("userId")
    val KEY_USER_FIRST_NAME = stringPreferencesKey("userFirstName")
    val KEY_USER_LAST_NAME = stringPreferencesKey("userLastName")
    val KEY_USER_IS_WORKER = booleanPreferencesKey("userIsWorked")
    val KEY_USER_PROFILE_IMAGE_URL = stringPreferencesKey("userProfileImageUrl")
}