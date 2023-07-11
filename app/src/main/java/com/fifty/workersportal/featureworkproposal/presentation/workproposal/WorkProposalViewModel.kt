package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class WorkProposalViewModel @Inject constructor(

) : ViewModel() {

    private val _proposalDateState = mutableStateOf<LocalDate?>(null)
    val proposalDateState: State<LocalDate?> = _proposalDateState

    private val _workerState = mutableStateOf<Worker?>(null)
    val workerState: State<Worker?> = _workerState

    private val _workProposalState = mutableStateOf(WorkProposalState())
    val workProposalState: State<WorkProposalState> = _workProposalState

    private val _isFullDay = mutableStateOf(true)
    val isFullDay: State<Boolean> = _isFullDay

    private val _isBeforeNoon = mutableStateOf(true)
    val isBeforeNoon: State<Boolean> = _isBeforeNoon

    private val _workDescriptionState = mutableStateOf(StandardTextFieldState())
    val workDescriptionState: State<StandardTextFieldState> = _workDescriptionState

    fun onEvent(event: WorkProposalEvent) {
        when (event) {
            is WorkProposalEvent.InputProposalDate -> {
                _proposalDateState.value = event.date
            }

            is WorkProposalEvent.InputWorkerId -> {

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
                _workProposalState.value = WorkProposalState()
            }
        }
    }
}