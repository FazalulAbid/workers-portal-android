package com.fifty.fixitnow.featureworkproposal.domain.usecase

import com.fifty.fixitnow.core.util.toMillis
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.fixitnow.featureworkproposal.data.util.WorkProposalError
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalResult
import com.fifty.fixitnow.featureworkproposal.domain.repository.WorkProposalRepository
import java.time.LocalDate

class SendWorkProposalUseCase(
    private val repository: WorkProposalRepository
) {

    suspend operator fun invoke(
        workDate: LocalDate?,
        workerId: String?,
        workCategoryId: String?,
        workDescription: String?,
        workAddress: LocalAddress?,
        isFullDay: Boolean,
        isBeforeNoon: Boolean,
        wage: Float?
    ): WorkProposalResult {
        val dateError =
            if (workDate == null) WorkProposalError.InvalidDate else null
        val workerError =
            if (workerId == null) WorkProposalError.InvalidWorker else null
        val workerCategoryError =
            if (workCategoryId == null) WorkProposalError.InvalidWorkCategory else null
        val workDescriptionError =
            if (workDescription.isNullOrBlank()) WorkProposalError.InvalidWorkDescription else null
        val wageError =
            if (wage == null || wage <= 0) WorkProposalError.InvalidWage else null
        val workAddressError =
            if (workAddress == null) WorkProposalError.InvalidWorkAddress else null

        if (dateError != null || workerError != null ||
            workerCategoryError != null || workDescriptionError != null ||
            wageError != null
        ) {
            return WorkProposalResult(
                workDateError = dateError,
                chosenWorkerError = workerError,
                workDescriptionError = workDescriptionError,
                workAddressError = workAddressError,
                chosenCategoryError = workerCategoryError,
                workWageError = wageError
            )
        } else {
            val request = SendWorkProposalRequest(
                workerId = workerId!!,
                chosenCategoryId = workCategoryId!!,
                wage = wage!!,
                isFullDay = isFullDay,
                isBeforeNoon = isBeforeNoon,
                proposedDate = workDate?.toMillis()!!,
                workDescription = workDescription!!,
                proposedAddressId = workAddress?.id!!
            )
            return WorkProposalResult(result = repository.sendWorkProposal(request))
        }
    }
}