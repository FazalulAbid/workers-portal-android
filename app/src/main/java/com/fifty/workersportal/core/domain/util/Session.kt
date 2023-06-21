package com.fifty.workersportal.core.domain.util

import androidx.compose.runtime.mutableStateOf
import com.fifty.workersportal.featureuser.domain.model.UserProfile

object Session {
    val userSession = mutableStateOf<UserProfile?>(null)
}