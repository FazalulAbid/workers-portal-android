package com.fifty.workersportal.featureauth.presentation.otp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.presentation.util.NavArgConstants
import com.fifty.workersportal.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(OtpState())
    val state: State<OtpState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _state.value = state.value.copy(
            countryCode = savedStateHandle
                .get<String>(NavArgConstants.NAV_ARG_COUNTRY_CODE) ?: "",
            phoneNumber = savedStateHandle
                .get<String>(NavArgConstants.NAV_ARG_PHONE_NUMBER) ?: ""
        )
    }

}