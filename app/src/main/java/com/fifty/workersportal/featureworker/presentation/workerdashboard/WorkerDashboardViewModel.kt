package com.fifty.workersportal.featureworker.presentation.workerdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.usecase.ToggleOpenToWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerDashboardViewModel @Inject constructor(
    private val toggleOpenToWorkUseCase: ToggleOpenToWorkUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getProfileDetailsUseCase: GetProfileDetailsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WorkerDashboardState())
    val state: State<WorkerDashboardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getUserDetails(getOwnUserIdUseCase())
        }
    }

    fun onEvent(event: WorkerDashboardEvent) {
        when (event) {
            is WorkerDashboardEvent.ToggleOpenToWork -> {
                toggleOpenToWork(event.value)
            }
        }
    }

    private suspend fun getUserDetails(userId: String) {
        _state.value = state.value.copy(
            isLoading = true
        )
        when (val result = getProfileDetailsUseCase(userId)) {
            is Resource.Success -> {
                _state.value = state.value.copy(
                    profile = result.data,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
                _eventFlow.emit(
                    UiEvent.MakeToast(result.uiText ?: UiText.unknownError()),
                )
                _eventFlow.emit(
                    UiEvent.NavigateUp
                )
            }
        }
    }

    private fun toggleOpenToWork(value: Boolean) {
        _state.value = state.value.copy(
            profile = state.value.profile?.copy(
                openToWork = value
            )
        )
        viewModelScope.launch {
            when (toggleOpenToWorkUseCase(value)) {
                is Resource.Success -> Unit

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        profile = state.value.profile?.copy(
                            openToWork = !value
                        )
                    )
                }
            }
        }
    }
}