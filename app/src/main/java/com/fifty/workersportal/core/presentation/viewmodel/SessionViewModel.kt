package com.fifty.workersportal.core.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.featureauth.domain.usecase.GetUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val getUserSession: GetUserSessionUseCase
) : ViewModel() {

    val userSession = mutableStateOf<UserSession?>(null)

    init {
        viewModelScope.launch {
            userSession.value = getUserSession()
        }
    }

    fun updateUserSession(session: UserSession) {
        userSession.value = session
    }
}