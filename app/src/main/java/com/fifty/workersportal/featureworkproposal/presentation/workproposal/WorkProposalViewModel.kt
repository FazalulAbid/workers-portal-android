package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class WorkProposalViewModel @Inject constructor(

) : ViewModel() {

    private val _proposalDateState = mutableStateOf<LocalDate?>(null)
    val proposalDateState: State<LocalDate?> = _proposalDateState

    private val _workProposalState = mutableStateOf(WorkProposalState())
    val workProposalState: State<WorkProposalState> = _workProposalState

    fun onEvent(event: WorkProposalEvent) {
        when (event) {
            is WorkProposalEvent.InputProposalDate -> {
                _proposalDateState.value = event.date
            }

            is WorkProposalEvent.InputWorkerId -> {

            }
        }
    }
}