package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import java.time.LocalDate

sealed class WorkProposalEvent {
    data class InputWorkDescription(val description: String) : WorkProposalEvent()
    data class InputProposalDate(val date: LocalDate) : WorkProposalEvent()
    data class InputWorkerId(val workerId: String) : WorkProposalEvent()
    data class ChangeIsFullDay(val isFullDay: Boolean) : WorkProposalEvent()
    data class ChangeIsBeforeNoon(val isBeforeNoon: Boolean) : WorkProposalEvent()
    object ClearProposalData : WorkProposalEvent()
}
