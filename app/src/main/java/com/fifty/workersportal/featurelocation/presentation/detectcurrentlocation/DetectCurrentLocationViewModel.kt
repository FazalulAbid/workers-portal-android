package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import android.R
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.usecase.CheckIfDeviceLocationEnabledUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressFromLatLngUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetCurrentLocationUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressFromAddressUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.SaveAddressUseCase
import com.fifty.workersportal.featurelocation.util.AddressError
import com.fifty.workersportal.featureworker.presentation.registerasworker.UpdateWorkerState
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
    private val getLocalAddressFromAddress: GetLocalAddressFromAddressUseCase,
    private val checkIfDeviceLocationEnabled: CheckIfDeviceLocationEnabledUseCase,
    private val saveAddress: SaveAddressUseCase
) : ViewModel() {

    val saveAddressAsItems = listOf("Home", "Work")

    private val _state = mutableStateOf(DetectCurrentLocationState())
    val state: State<DetectCurrentLocationState> = _state

    private val _addressTitleState = mutableStateOf(StandardTextFieldState())
    val addressTitleState: State<StandardTextFieldState> = this._addressTitleState

    private val _completeAddressState = mutableStateOf(StandardTextFieldState())
    val completeAddressState: State<StandardTextFieldState> = _completeAddressState

    private val _floorState = mutableStateOf(StandardTextFieldState())
    val floorState: State<StandardTextFieldState> = _floorState

    private val _landmarkState = mutableStateOf(StandardTextFieldState())
    val landmarkState: State<StandardTextFieldState> = _landmarkState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<AddressError>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        if (checkIfDeviceLocationEnabled()) {
            getCurrentDeviceLocation()
        } else {
            viewModelScope.launch {
                _eventFlow.emit(
                    UiEvent.MakeToast(UiText.DynamicString("Location is not enabled!"))
                )
            }
        }
    }

    fun onEvent(event: DetectCurrentLocationEvent) {
        when (event) {
            DetectCurrentLocationEvent.CurrentLocation -> {
                getCurrentDeviceLocation()
            }

            is DetectCurrentLocationEvent.EnterAddressTitle -> {
                _addressTitleState.value = addressTitleState.value.copy(
                    text = event.title
                )
            }

            is DetectCurrentLocationEvent.EnterCompleteAddress -> {
                _completeAddressState.value = completeAddressState.value.copy(
                    text = event.address
                )
            }

            is DetectCurrentLocationEvent.EnterFloor -> {
                _floorState.value = floorState.value.copy(
                    text = event.floor
                )
            }

            is DetectCurrentLocationEvent.EnterLandmark -> {
                _landmarkState.value = landmarkState.value.copy(
                    text = event.landmark
                )
            }

            is DetectCurrentLocationEvent.SelectCurrentCameraPosition -> {
                _state.value = state.value.copy(
                    currentLatLong = LatLng(event.lat, event.lng)
                )
            }

            DetectCurrentLocationEvent.SaveAddress -> {
                saveLocalAddress()
            }

            is DetectCurrentLocationEvent.SelectSaveAddressAs -> {
                _addressTitleState.value = addressTitleState.value.copy(
                    text = event.value
                )
            }

        }
    }

    private fun saveLocalAddress() {
        _state.value = state.value.copy(
            isAddressLoading = true
        )
        val localAddress = _state.value.localAddress?.copy(
            title = _addressTitleState.value.text,
            completeAddress = _completeAddressState.value.text,
            floor = _floorState.value.text,
            landmark = _landmarkState.value.text
        )
        _state.value = state.value.copy(
            localAddress = localAddress
        )

        _state.value.localAddress?.let { address ->
            viewModelScope.launch {
                val saveAddressResult = saveAddress(address)

                if (saveAddressResult.titleError != null) {
                    _errorFlow.emit(
                        saveAddressResult.titleError
                    )
                } else if (saveAddressResult.completeAddressError != null) {
                    _errorFlow.emit(
                        saveAddressResult.completeAddressError
                    )
                } else {
                    when (saveAddressResult.result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                isAddressLoading = false
                            )
                            _eventFlow.emit(
                                UiEvent.NavigateUp
                            )
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.MakeToast(
                                    uiText = saveAddressResult.result.uiText
                                        ?: UiText.unknownError()
                                )
                            )
                            _state.value = state.value.copy(
                                isAddressLoading = false
                            )
                        }


                        null -> {
                            _state.value = state.value.copy(
                                isAddressLoading = false
                            )
                        }
                    }
                }
            }
        }
        _state.value = state.value.copy(
            isAddressLoading = false
        )
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