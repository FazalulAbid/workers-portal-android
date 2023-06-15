package com.fifty.workersportal.featurelocation.presentation.selectlocation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressesOfUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectLocationViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getAddressesOfUser: GetAddressesOfUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SelectLocationState())
    val state: State<SelectLocationState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getAddresses(getOwnUserId())
        }
    }

    private suspend fun getAddresses(userId: String) {
        _state.value = state.value.copy(
            isLoading = true
        )
        when (val result = getAddressesOfUser(userId)) {
            is Resource.Success -> {
                _state.value = state.value.copy(
                    localAddresses = result.data ?: emptyList(),
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                )
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }
}