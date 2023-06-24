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
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getUserProfileDetails: GetProfileDetailsUseCase,
    private val getSampleWorksUseCase: GetSampleWorksUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var userId = ""
    private var ownUserId = ""

    private val _state = mutableStateOf(WorkerProfileState())
    val state: State<WorkerProfileState> = _state

    var sampleWorks: Flow<PagingData<SampleWork>> = flowOf(PagingData.empty())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            ownUserId = getOwnUserId().trim()
            userId = savedStateHandle.get<String>("userId") ?: ownUserId
            getProfile(userId, ownUserId)
            _state.value = state.value.copy(
                isOwnProfile = state.value.profile?.id?.equals(ownUserId) == true
            )
            getSampleWorksPaginated(userId)
        }
    }

    fun onEvent(event: WorkerProfileEvent) {
        when (event) {
            WorkerProfileEvent.UpdateSampleWorks -> {
                getSampleWorksPaginated(userId)
            }

            WorkerProfileEvent.UpdateWorkerProfileDetails -> {
                getProfile(userId, ownUserId)
            }
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

    private fun getSampleWorksPaginated(userId: String) {
        _state.value = state.value.copy(
            isSampleWorksLoading = true
        )
        Log.d("Hello", "getSampleWorksPaginated: Worked")
        sampleWorks = getSampleWorksUseCase(userId).cachedIn(viewModelScope)
        _state.value = state.value.copy(
            isSampleWorksLoading = false
        )
    }
}