package com.fifty.fixitnow.featureauth.presentation.splash

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.ReadOnBoardingStateUseCase
import com.fifty.fixitnow.featureauth.presentation.onboarding.OnBoardingEvent
import com.fifty.fixitnow.featurelocation.domain.usecase.GetLocalAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase,
    private val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase
) : ViewModel() {
    //    private val _isAuthenticated = MutableStateFlow(value = UserAuthState.UNKNOWN)
//    val isAuthenticated: StateFlow<UserAuthState> = _isAuthenticated.asStateFlow()

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

//    private val _startDestination: MutableState<String?> = mutableStateOf(null)
//    val startDestination: State<String?> = _startDestination

    init {
        viewModelScope.launch {
            readOnBoardingStateUseCase().take(1).collect { completed ->
                Log.d("Hello", "Emitted from Data Store")
                if (completed) {
                    checkAuthentication()
                } else {
                    _startDestination.emit(NavigationParent.OnBoarding.route)
                }
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.SplashLoadingComplete -> {
                _isLoading.value = false
            }
        }
    }

    private suspend fun checkAuthentication() {
        when (val result = authenticate()) {
            is Resource.Success -> {
                val userSession = result.data
                if (userSession != null) {
                    Session.userSession.value = userSession
                    userSession.selectedAddress?.let {
                        getLocalAddress(userSession.selectedAddress)
                    }
                    _startDestination.emit(NavigationParent.Home.route)
                } else {
                    _startDestination.emit(NavigationParent.Auth.route)
                }
            }

            is Resource.Error -> {
                _startDestination.emit(NavigationParent.Auth.route)
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