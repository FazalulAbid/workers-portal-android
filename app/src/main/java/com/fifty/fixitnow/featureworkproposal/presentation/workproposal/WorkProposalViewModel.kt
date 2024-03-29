package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.domain.util.HttpError
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.fifty.fixitnow.featureworkproposal.data.util.WorkProposalError
import com.fifty.fixitnow.featureworkproposal.domain.usecase.SendWorkProposalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkProposalViewModel @Inject constructor(
    private val sendWorkProposalUseCase: SendWorkProposalUseCase
) : ViewModel() {

    val isWorkProposalSentDialogDisplayed = MutableLiveData(false)

    private val _proposalDateState = mutableStateOf<LocalDate?>(null)
    val proposalDateState: State<LocalDate?> = _proposalDateState

    private val _workerState = mutableStateOf<Worker?>(null)
    val workerState: State<Worker?> = _workerState

    private val _workCategory = mutableStateOf<WorkerCategory?>(null)
    val workCategory: State<WorkerCategory?> = _workCategory

    private val _workDescriptionState = mutableStateOf(StandardTextFieldState())
    val workDescriptionState: State<StandardTextFieldState> = _workDescriptionState

    private val _isFullDay = mutableStateOf(true)
    val isFullDay: State<Boolean> = _isFullDay

    private val _isBeforeNoon = mutableStateOf(true)
    val isBeforeNoon: State<Boolean> = _isBeforeNoon

    private val _state = mutableStateOf(WorkProposalState())
    val state: State<WorkProposalState> = _state

    private val _errorFlow = MutableSharedFlow<WorkProposalError>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: WorkProposalEvent) {
        when (event) {
            is WorkProposalEvent.InputProposalDate -> {
                _proposalDateState.value = event.date
            }

            is WorkProposalEvent.SelectWorker -> {
                _workerState.value = event.worker
            }

            is WorkProposalEvent.SelectCategory -> {
                _workCategory.value = event.category
            }

            is WorkProposalEvent.ChangeIsFullDay -> {
                _isFullDay.value = event.isFullDay
            }

            is WorkProposalEvent.ChangeIsBeforeNoon -> {
                _isBeforeNoon.value = event.isBeforeNoon
            }

            is WorkProposalEvent.InputWorkDescription -> {
                _workDescriptionState.value = workDescriptionState.value.copy(
                    text = event.description
                )
            }

            WorkProposalEvent.ClearProposalData -> {
                _workDescriptionState.value = StandardTextFieldState()
                _isFullDay.value = true
                _isBeforeNoon.value = true
                _proposalDateState.value = null
                _state.value = WorkProposalState()
            }

            WorkProposalEvent.SendProposal -> {
                viewModelScope.launch {
                    sendWorkProposal()
                }
            }
        }
    }

    private suspend fun sendWorkProposal() {
        val sendWorkProposalResult = sendWorkProposalUseCase(
            workDate = _proposalDateState.value,
            workerId = _workerState.value?.workerId,
            workCategoryId = _workCategory.value?.id,
            workDescription = _workDescriptionState.value.text,
            workAddress = Session.selectedAddress.value,
            isFullDay = _isFullDay.value,
            isBeforeNoon = _isBeforeNoon.value,
            wage = if (_isFullDay.value) {
                _workCategory.value?.dailyWage?.toFloat()
            } else _workCategory.value?.hourlyWage?.toFloat()
        )
        if (sendWorkProposalResult.workDateError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.workDateError
            )
        } else if (sendWorkProposalResult.chosenWorkerError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.chosenWorkerError
            )
        } else if (sendWorkProposalResult.chosenCategoryError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.chosenCategoryError
            )
        } else if (sendWorkProposalResult.workDescriptionError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.workDescriptionError
            )
        } else if (sendWorkProposalResult.workAddressError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.workAddressError
            )
        } else if (sendWorkProposalResult.workWageError != null) {
            _errorFlow.emit(
                sendWorkProposalResult.workWageError
            )
        } else {
            when (val result = sendWorkProposalResult.result) {
                is Resource.Success -> {
                    result.data?.let {
                        _state.value = state.value.copy(
                            workProposal = it
                        )
                        _eventFlow.emit(UiEvent.SentWorkProposal)
                    }
                }

                is Resource.Error -> {
                    if (result.errorCode == HttpError.WORKER_DATE_CONFLICT) {
                        _errorFlow.emit(WorkProposalError.WorkerDateConflict)
                    } else {
                        _eventFlow.emit(UiEvent.MakeToast(result.uiText ?: UiText.unknownError()))
                    }
                }

                null -> {
                    _eventFlow.emit(UiEvent.MakeToast(UiText.unknownError()))
                }
            }
        }
    }
}