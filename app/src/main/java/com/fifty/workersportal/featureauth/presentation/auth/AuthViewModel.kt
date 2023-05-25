package com.fifty.workersportal.featureauth.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.usecase.GetOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getOtpUseCase: GetOtpUseCase
) : ViewModel() {

    private val _countryCode = mutableStateOf(Constants.DEFAULT_COUNTRY_CODE)
    var countryCode: State<String> = _countryCode

    private val _countryFlagUrl = mutableStateOf(Constants.DEFAULT_COUNTRY_FLAG_URL)
    var countryFlagUrl: State<String> = _countryFlagUrl

    private val _countryName = mutableStateOf(Constants.DEFAULT_COUNTRY_NAME)
    var countryName: State<String> = _countryName

    private val _phoneNumberState = mutableStateOf(StandardTextFieldState())
    val phoneNumberState: State<StandardTextFieldState> = _phoneNumberState

    private val _countryCodeState = mutableStateOf<String>("")
    val countryCodeState: State<String> = _countryCodeState

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredPhoneNumber -> {
                _phoneNumberState.value = phoneNumberState.value.copy(
                    text = event.phoneNumber
                )
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

    private fun getOtp(countryCode: String, phoneNumber: String) {
        viewModelScope.launch {
            _eventFlow.emit(
                UiEvent.HideKeyboard
            )
            _state.value = state.value.copy(
                isGetOtpLoading = true
            )
            val result = getOtpUseCase(
                countryCode = countryCode,
                phoneNumber = phoneNumber
            )
            when (result) {
                is Resource.Success -> {
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