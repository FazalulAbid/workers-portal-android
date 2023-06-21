package com.fifty.workersportal.featureauth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.workersportal.featureauth.domain.usecase.SaveUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isAuthenticated = MutableStateFlow(value = UserAuthState.UNKNOWN)
    val isAuthenticated: StateFlow<UserAuthState> = _isAuthenticated.asStateFlow()

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.CheckAuthentication -> {
                viewModelScope.launch {
                    when (val result = authenticate()) {
                        is Resource.Success -> {
                            val userSession = result.data
                            userSession?.let {
                                Session.userSession.value = it
                                _isAuthenticated.emit(UserAuthState.AUTHENTICATED)
                            } ?: _isAuthenticated.emit(UserAuthState.UNAUTHENTICATED)
                        }

                        is Resource.Error -> {
                            _isAuthenticated.emit(UserAuthState.UNAUTHENTICATED)
                        }
                    }
                }
            }
        }
    }
}