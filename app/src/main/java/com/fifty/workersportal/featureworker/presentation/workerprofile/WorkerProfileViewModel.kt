package com.fifty.workersportal.featureworker.presentation.workerprofile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.usecase.GetSampleWorksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerProfileViewModel @Inject constructor(
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getUserProfileDetails: GetProfileDetailsUseCase,
    private val getSampleWorksUseCase: GetSampleWorksUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(WorkerProfileState())
    val state: State<WorkerProfileState> = _state

    var sampleWorks: Flow<PagingData<SampleWork>> = flowOf(PagingData.empty())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getSampleWorksPaginated(savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase())
        }
    }

    fun onEvent(event: WorkerProfileEvent) {
        when (event) {
            WorkerProfileEvent.UpdateSampleWorks -> {
                viewModelScope.launch {
                    getSampleWorksPaginated(getOwnUserIdUseCase())
                }
            }

            WorkerProfileEvent.UpdateWorkerProfileDetails -> {
                viewModelScope.launch {
                    getProfile(getOwnUserIdUseCase())
                }
            }
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val nonNullUserId = userId ?: getOwnUserIdUseCase()
            when (val result = getUserProfileDetails(
                userId = nonNullUserId
            )) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isOwnProfile = nonNullUserId == Session.userSession.value?.id,
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

    private fun getSampleWorksPaginated(userId: String) {
        _state.value = state.value.copy(
            isSampleWorksLoading = true
        )
        sampleWorks = getSampleWorksUseCase(userId).cachedIn(viewModelScope)
        _state.value = state.value.copy(
            isSampleWorksLoading = false
        )
    }
}