package com.fifty.fixitnow.featurehistory.domain.repository

import com.fifty.fixitnow.core.util.SimpleResource

interface WorkHistoryRepository {

    suspend fun getWorkHistoryReport(
        fromDate: Long,
        toDate: Long,
        isWorkHistoryNeeded: Boolean,
        isHiringHistoryNeeded: Boolean,
        isPendingWorksNeeded: Boolean,
        isCancelledWorksNeeded: Boolean,
        isCompletedWorksNeeded: Boolean,
        page: Int
    ): SimpleResource
}