package com.fifty.fixitnow.featureworker.presentation.workerprofile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fifty.fixitnow.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.FavouriteToggle
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.usecase.GetSampleWorksUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.GetWorkerDetailsUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
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
    private val getSampleWorksUseCase: GetSampleWorksUseCase,
    private val toggleFavouriteWorkerUseCase: ToggleFavouriteWorkerUseCase,
    private val getWorkerDetailsUseCase: GetWorkerDetailsUseCase,
    private val favouriteToggle: FavouriteToggle,
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

            is WorkerProfileEvent.ToggleFavourite -> {
                _state.value = _state.value.copy(
                    worker = _state.value.worker?.copy(
                        isFavourite = _state.value.worker?.isFavourite != true
                    )
                )
                state.value.worker?.let { worker ->
                    toggleFavouriteWorker(
                        worker.workerId,
                        _state.value.worker?.isFavourite != true
                    )
                }
            }

            WorkerProfileEvent.UpdateWorkerProfileDetails -> {
                viewModelScope.launch {
                    getProfile(getOwnUserIdUseCase())
                }
            }

            is WorkerProfileEvent.ClickSampleWork -> {
                _state.value = state.value.copy(
                    clickedSampleWork = event.sampleWork
                )
            }
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val nonNullUserId = userId ?: getOwnUserIdUseCase()
            when (val result = getWorkerDetailsUseCase(workerId = nonNullUserId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        worker = result.data,
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

    private fun toggleFavouriteWorker(workerId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            when (val result = toggleFavouriteWorkerUseCase(workerId, isFavourite)) {
                is Resource.Success -> Unit

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        worker = _state.value.worker?.copy(
                            isFavourite = !isFavourite
                        )
                    )
                    _eventFlow.emit(UiEvent.MakeToast(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }
}