package com.fifty.workersportal.featureworker.presentation.workerprofile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
    private val getUserProfileDetails: GetUserProfileDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(WorkerProfileState())
    val state: State<WorkerProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val ownUserId = getOwnUserId().trim()
            val userId = savedStateHandle.get<String>("userId") ?: ownUserId
            getProfile(userId, ownUserId)
            _state.value = state.value.copy(
                isOwnProfile = state.value.profile?.id?.equals(ownUserId) == true
            )
        }
    }

    private fun getProfile(userId: String, ownUserId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = getUserProfileDetails(userId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isOwnProfile = userId == ownUserId,
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