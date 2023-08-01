package com.fifty.fixitnow.featurehistory.domain.repository

import androidx.paging.PagingData
import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import kotlinx.coroutines.flow.Flow

interface WorkHistoryRepository {

    fun getWorkHistoryPaged(
        fromDate: Long,
        toDate: Long,
        isWorkHistoryNeeded: Boolean,
        isHiringHistoryNeeded: Boolean,
        isPendingWorksNeeded: Boolean,
        isCancelledWorksNeeded: Boolean,
        isCompletedWorksNeeded: Boolean
    ): Flow<PagingData<WorkHistory>>
}