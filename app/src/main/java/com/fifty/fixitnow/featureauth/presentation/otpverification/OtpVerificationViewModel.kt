package com.fifty.fixitnow.featureauth.presentation.otpverification

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.usecase.AuthUseCases
import com.fifty.fixitnow.featureauth.domain.usecase.SaveAccessTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveRefreshTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveUserIdUseCase
import com.fifty.fixitnow.featureauth.presentation.component.NAV_ARG_COUNTRY_CODE
import com.fifty.fixitnow.featureauth.presentation.component.NAV_ARG_PHONE_NUMBER
import com.fifty.fixitnow.featurelocation.domain.usecase.GetLocalAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authUseCases: AuthUseCases,
    private val saveAccessToken: SaveAccessTokenUseCase,
    private val saveRefreshToken: SaveRefreshTokenUseCase,
    private val saveUserSession: SaveUserIdUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase
) : ViewModel() {

    private var resendTimer: CountDownTimer? = null
    private val _timerValue = MutableStateFlow(20)
    val timerValue: StateFlow<Int> = _timerValue

    private val _state = mutableStateOf(OtpVerificationState())
    val state: State<OtpVerificationState> = _state

    private val _otpTextFieldState = mutableStateOf(StandardTextFieldState())
    val otpTextFieldState: State<StandardTextFieldState> = _otpTextFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _state.value = state.value.copy(
            countryCode = savedStateHandle
                .get<String>(NAV_ARG_COUNTRY_CODE) ?: "",
            phoneNumber = savedStateHandle
                .get<String>(NAV_ARG_PHONE_NUMBER) ?: ""
        )
    }

    fun onEvent(event: OtpVerificationEvent) {
        when (event) {
            is OtpVerificationEvent.EnterOtp -> {
                _otpTextFieldState.value = otpTextFieldState.value.copy(
                    text = event.otp
                )
                if (_otpTextFieldState.value.text.length == 6) {
                    verifyOtp()
                }
            }

            OtpVerificationEvent.ResendOtp -> {
                startResendTimer()
                resendOtp()
            }

            OtpVerificationEvent.VerifyOtp -> {
                verifyOtp()
            }

        }
    }

    private fun verifyOtp() {
        viewModelScope.launch {
            val result = authUseCases.verifyOtp(
                countryCode = state.value.countryCode,
                phoneNumber = state.value.phoneNumber,
                otpCode = _otpTextFieldState.value.text
            )
            when (result) {
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
                    _otpTextFieldState.value = otpTextFieldState.value.copy(
                        text = ""
                    )
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
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

    private fun resendOtp() {
        viewModelScope.launch {
            val result = authUseCases.getOtp(
                countryCode = state.value.countryCode,
                phoneNumber = state.value.phoneNumber
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            uiText = UiText.StringResource(R.string.otp_resend_success)
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
        }
    }

    fun startResendTimer() {
        _timerValue.value = Constants.OTP_RESEND_INTERVAL
        resendTimer = object : CountDownTimer((_timerValue.value * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerValue.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _timerValue.value = 0
            }
        }.start()
    }

    fun stopResendTimer() {
        resendTimer?.cancel()
        resendTimer = null
    }
}