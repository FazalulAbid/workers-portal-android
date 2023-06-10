package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressFromLatLngUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetCurrentLocationUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressFromAddressUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetectCurrentLocationViewModel @Inject constructor(
    private val getCurrentLocation: GetCurrentLocationUseCase,
    private val getAddressFromLatLng: GetAddressFromLatLngUseCase,
    private val getLocalAddressFromAddress: GetLocalAddressFromAddressUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DetectCurrentLocationState())
    val state: State<DetectCurrentLocationState> = _state

    private val _eventFlow =
        MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getCurrentDeviceLocation()
    }

    fun onEvent(event: DetectCurrentLocationEvent) {
        when (event) {
            DetectCurrentLocationEvent.CurrentLocation -> {
                getCurrentDeviceLocation()
            }
        }
    }

    private fun getCurrentDeviceLocation() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val currentLocation = getCurrentLocation()
            currentLocation?.let {
                _state.value = state.value.copy(
                    currentLatLong = LatLng(it.latitude, it.longitude),
                    address = getAddressFromLatLng(LatLng(it.latitude, it.longitude))
                )
            }
            _eventFlow.emit(UiEvent.OnCurrentLocation)
            _state.value.address?.let {
                val localAddress = getLocalAddressFromAddress(it)
                _state.value = _state.value.copy(
                    localAddress = localAddress
                )
            }
            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }

}