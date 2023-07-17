package com.fifty.fixitnow.featurelocation.presentation.selectlocation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featurelocation.domain.usecase.GetAddressesOfUserUseCase
import com.fifty.fixitnow.featurelocation.domain.usecase.SelectLocalAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectLocationViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getAddressesOfUser: GetAddressesOfUserUseCase,
    private val selectAddressUseCase: SelectLocalAddressUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SelectLocationState())
    val state: State<SelectLocationState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAddressesForOwnUser()
    }

    fun onEvent(event: SelectLocationEvent) {
        when (event) {
            SelectLocationEvent.RefreshAddressList -> {
                getAddressesForOwnUser()
            }

            is SelectLocationEvent.SelectLocalAddress -> {
                selectLocalAddress(event.addressId)
            }
        }
    }

    private fun getAddressesForOwnUser() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val userId = getOwnUserId()
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

    private fun selectLocalAddress(addressId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = selectAddressUseCase(addressId)) {
                is Resource.Success -> {
                    Session.selectedAddress.value = result.data
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.NavigateUp)
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
}