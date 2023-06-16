package com.fifty.workersportal.featureworker.presentation.workerprofile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.usecase.GetUserProfileDetailsUseCase
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerProfileViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getUserProfileDetails: GetUserProfileDetailsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WorkerProfileState())
    val state: State<WorkerProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = getUserProfileDetails(
                userId ?: getOwnUserId()
            )
            when (result) {
                is Resource.Success -> {
                    Log.d("Hello", "getProfile: ${result.data}")
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
                        UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }
}