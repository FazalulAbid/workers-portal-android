package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import java.time.LocalDate

sealed class WorkProposalEvent {
    data class InputProposalDate(val date: LocalDate) : WorkProposalEvent()
    data class InputWorkerId(val workerId: String) : WorkProposalEvent()
}
