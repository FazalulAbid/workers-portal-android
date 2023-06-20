package com.fifty.workersportal.featureworker.presentation.workerdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.usecase.ToggleOpenToWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerDashboardViewModel @Inject constructor(
    private val toggleOpenToWorkUseCase: ToggleOpenToWorkUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WorkerDashboardState())
    val state: State<WorkerDashboardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: WorkerDashboardEvent) {
        when (event) {
            is WorkerDashboardEvent.ToggleOpenToWork -> {
                toggleOpenToWork(event.value)
            }
        }
    }

    private fun toggleOpenToWork(value: Boolean) {
        _state.value = state.value.copy(
            openToWork = value
        )
        viewModelScope.launch {
            when (toggleOpenToWorkUseCase(value)) {
                is Resource.Success -> Unit

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        openToWork = !value
                    )
                }
            }
        }
    }
}