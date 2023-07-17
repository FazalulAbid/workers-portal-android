package com.fifty.fixitnow.featureauth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.fixitnow.featurelocation.domain.usecase.GetLocalAddressUseCase
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
    private val getLocalAddressUseCase: GetLocalAddressUseCase
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
                                userSession.selectedAddress?.let {
                                    getLocalAddress(userSession.selectedAddress)
                                }
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

    private suspend fun getLocalAddress(addressId: String) {
        when (val result = getLocalAddressUseCase(addressId)) {
            is Resource.Success -> {
                Session.selectedAddress.value = result.data
            }

            is Resource.Error -> {

            }
        }
    }
}