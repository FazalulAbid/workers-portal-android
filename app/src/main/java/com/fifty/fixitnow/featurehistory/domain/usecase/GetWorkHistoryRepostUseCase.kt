package com.fifty.fixitnow.featurehistory.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featurehistory.domain.repository.WorkHistoryRepository

class GetWorkHistoryRepostUseCase(
    private val repository: WorkHistoryRepository
) {
    suspend operator fun invoke(): SimpleResource =
        repository.getWorkHistoryReport(
            fromDate = 1690243100000,
            toDate = 1690329700000,
            isWorkHistoryNeeded = true,
            isHiringHistoryNeeded = true,
            isPendingWorksNeeded = true,
            isCancelledWorksNeeded = true,
            isCompletedWorksNeeded = true,
            page = 0
        )
}