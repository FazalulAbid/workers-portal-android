package com.fifty.workersportal.core.domain.util

import androidx.compose.runtime.mutableStateOf
import com.fifty.workersportal.core.domain.model.UserSession

object Session {
    val userSession = mutableStateOf<UserSession?>(null)
}