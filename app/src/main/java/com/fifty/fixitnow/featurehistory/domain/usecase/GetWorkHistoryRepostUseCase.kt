package com.fifty.fixitnow.featurehistory.domain.usecase

import androidx.paging.PagingData
import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import com.fifty.fixitnow.featurehistory.domain.repository.WorkHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetWorkHistoryRepostUseCase(
    private val repository: WorkHistoryRepository
) {

    operator fun invoke(
        fromDate: Long,
        toDate: Long,
        isWorkHistoryNeeded: Boolean,
        isHiringHistoryNeeded: Boolean,
        isPendingWorksNeeded: Boolean,
        isCancelledWorksNeeded: Boolean,
        isCompletedWorksNeeded: Boolean
    ): Flow<PagingData<WorkHistory>> =
        repository.getWorkHistoryPaged(
            fromDate = fromDate,
            toDate = toDate,
            isWorkHistoryNeeded = isWorkHistoryNeeded,
            isHiringHistoryNeeded = isHiringHistoryNeeded,
            isPendingWorksNeeded = isPendingWorksNeeded,
            isCancelledWorksNeeded = isCancelledWorksNeeded,
            isCompletedWorksNeeded = isCompletedWorksNeeded
        )
}