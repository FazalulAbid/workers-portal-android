package com.fifty.fixitnow.core.domain.util

import androidx.compose.runtime.mutableStateOf
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featureuser.domain.model.UserProfile

object Session {
    val userSession = mutableStateOf<UserProfile?>(null)
    val selectedAddress = mutableStateOf<LocalAddress?>(null)
    var accessToken: String? = null
}