package com.fifty.fixitnow.featureworker.presentation.workerdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.fixitnow.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.PagingState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.DefaultPaginator
import com.fifty.fixitnow.core.util.Event
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.usecase.ToggleOpenToWorkUseCase
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalForWorker
import com.fifty.fixitnow.featureworkproposal.domain.usecase.AcceptOrRejectWorkProposalUseCase
import com.fifty.fixitnow.featureworkproposal.domain.usecase.GetWorkProposalsForWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkerDashboardViewModel @Inject constructor(
    private val toggleOpenToWorkUseCase: ToggleOpenToWorkUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getProfileDetailsUseCase: GetProfileDetailsUseCase,
    private val getWorkProposalsForWorkerUseCase: GetWorkProposalsForWorkerUseCase,
    private val acceptOrRejectWorkProposalUseCase: AcceptOrRejectWorkProposalUseCase
) : ViewModel() {

    private val _cards = MutableStateFlow(listOf<WorkProposalForWorker>())
    val cards: StateFlow<List<WorkProposalForWorker>> get() = _cards

    private val _revealedCard = mutableStateOf<WorkProposalForWorker?>(null)
    val revealedCard: State<WorkProposalForWorker?> = _revealedCard

    private val _state = mutableStateOf(WorkerDashboardState())
    val state: State<WorkerDashboardState> = _state

    var pagingState by mutableStateOf(PagingState<WorkProposalForWorker>())

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val workProposalsPaginator = DefaultPaginator(
        initialKey = pagingState.page,
        onLoadUpdated = {
            pagingState = pagingState.copy(isLoading = true)
        },
        onRequest = { nextPage ->
            getWorkProposalsForWorkerUseCase(
                page = nextPage
            )
        },
        getNextKey = {
            pagingState.page + 1
        },
        onError = {
            pagingState = pagingState.copy(error = it)
            _eventFlow.emit(UiEvent.MakeToast(it))
        },
        onSuccess = { items, newKey ->
            pagingState = pagingState.copy(
                items = pagingState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        viewModelScope.launch {
            getUserDetails(getOwnUserIdUseCase())
        }
        loadNextWorkers()
    }

    fun onEvent(event: WorkerDashboardEvent) {
        when (event) {
            is WorkerDashboardEvent.ToggleOpenToWork -> {
                toggleOpenToWork(event.value)
            }

            WorkerDashboardEvent.UpdateSelectedAddress -> {
                getLocalAddress()
            }

            WorkerDashboardEvent.UpdateUserDetails -> {
                viewModelScope.launch {
                    getUserDetails(getOwnUserIdUseCase())
                }
            }

            WorkerDashboardEvent.LoadWorkProposal -> {
                viewModelScope.launch {
                    loadNextWorkers(resetPaging = true)
                }
            }

            is WorkerDashboardEvent.AcceptWorkProposal -> {
                acceptOrRejectWorkProposal(event.workProposalId, true)
            }

            is WorkerDashboardEvent.RejectWorkProposal -> {
                acceptOrRejectWorkProposal(event.workProposalId, false)
            }
        }
    }

    fun onItemExpanded(card: WorkProposalForWorker) {
        _revealedCard.value = card
    }

    fun onItemCollapsed(card: WorkProposalForWorker) {
        _revealedCard.value = null
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

    private fun acceptOrRejectWorkProposal(workProposalId: String, isAcceptWorkProposal: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = acceptOrRejectWorkProposalUseCase(
                workProposalId = workProposalId,
                isAcceptProposal = isAcceptWorkProposal
            )
            when (result) {
                is Resource.Success -> {
                    if (isAcceptWorkProposal) {
                        _eventFlow.emit(WorkerDashboardUiEvent.WorkProposalAccepted)
                    } else {
                        _eventFlow.emit(WorkerDashboardUiEvent.WorkProposalRejected)
                    }
                }

                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.MakeToast(result.uiText ?: UiText.unknownError()))
                }
            }
            loadNextWorkers(true)
            _state.value = state.value.copy(
                isLoading = false
            )
        }
    }

    private fun getLocalAddress() {
        _state.value = state.value.copy(
            selectedLocalAddress = Session.selectedAddress.value
        )
    }

    fun loadNextWorkers(resetPaging: Boolean = false) {
        if (resetPaging) {
            workProposalsPaginator.reset()
            pagingState = PagingState()
        }
        viewModelScope.launch {
            workProposalsPaginator.loadNextItems()
        }
    }
}