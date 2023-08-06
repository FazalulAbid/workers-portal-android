package com.fifty.fixitnow.featureauth.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.usecase.AuthUseCases
import com.fifty.fixitnow.featureauth.domain.usecase.SaveAccessTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveRefreshTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveUserIdUseCase
import com.fifty.fixitnow.featurelocation.domain.usecase.GetLocalAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val saveAccessToken: SaveAccessTokenUseCase,
    private val saveRefreshToken: SaveRefreshTokenUseCase,
    private val saveUserSession: SaveUserIdUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase
) : ViewModel() {

    private val _countryCode = mutableStateOf(Constants.DEFAULT_COUNTRY_CODE)
    var countryCode: State<String> = _countryCode

    private val _countryFlagUrl = mutableStateOf(Constants.DEFAULT_COUNTRY_FLAG_URL)
    var countryFlagUrl: State<String> = _countryFlagUrl

    private val _countryName = mutableStateOf(Constants.DEFAULT_COUNTRY_NAME)
    var countryName: State<String> = _countryName

    private val _phoneNumberState = mutableStateOf(StandardTextFieldState())
    val phoneNumberState: State<StandardTextFieldState> = _phoneNumberState

    private val _countryCodeState = mutableStateOf("")

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    var isGoogleAuthLoading = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredPhoneNumber -> {
                _phoneNumberState.value = phoneNumberState.value.copy(
                    text = event.phoneNumber
                )
            }

            is AuthEvent.OnGoogleSignIn -> {
                if (event.token != null) {
                    googleSignIn(event.token)
                }
            }

            is AuthEvent.GetOtp -> {
                getOtp(
                    countryCode = countryCode.value,
                    phoneNumber = phoneNumberState.value.text
                )
            }

            is AuthEvent.ClearPhoneNumber -> {
                _phoneNumberState.value = phoneNumberState.value.copy(
                    text = ""
                )
            }
        }
    }

    private fun googleSignIn(googleToken: String) {
        viewModelScope.launch {
            isGoogleAuthLoading.value = true
            when (val result = authUseCases.googleSignIn(googleToken)) {
                is Resource.Success -> {
                    result.data?.let { otpVerification ->
                        saveAccessToken(otpVerification.accessToken)
                        saveRefreshToken(otpVerification.refreshToken)
                        saveUserSession(otpVerification.user.id)
                        Session.userSession.value = otpVerification.user.toProfile().toUserProfile()
                        otpVerification.user.selectedAddress?.let {
                            getLocalAddress(it)
                        }
                        _eventFlow.emit(
                            UiEvent.OnLogin
                        )
                    } ?: _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
            isGoogleAuthLoading.value = false
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

    private fun getOtp(countryCode: String, phoneNumber: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isGetOtpLoading = true
            )
            val result = authUseCases.getOtp(
                countryCode = countryCode,
                phoneNumber = phoneNumber
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.HideKeyboard
                    )
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.OtpVerificationScreen.route +
                                    "/${countryCode}/${phoneNumberState.value.text}"
                        )
                    )
                    _state.value = state.value.copy(
                        isGetOtpLoading = false
                    )
                    _phoneNumberState.value = phoneNumberState.value.copy(
                        text = ""
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _state.value = state.value.copy(
                        isGetOtpLoading = false
                    )
                }
            }
        }
    }
}