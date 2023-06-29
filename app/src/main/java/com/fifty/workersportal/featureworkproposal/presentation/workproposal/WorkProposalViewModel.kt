package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WorkProposalViewModel @Inject constructor(

) : ViewModel() {

    private val _workProposalState = mutableStateOf(WorkProposalState())
    val workProposalState: State<WorkProposalState> = _workProposalState


}