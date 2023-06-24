package com.fifty.workersportal.core.domain.util

import androidx.compose.runtime.mutableStateOf
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureuser.domain.model.UserProfile

object Session {
    val userSession = mutableStateOf<UserProfile?>(null)
    val selectedAddress = mutableStateOf<LocalAddress?>(null)
}