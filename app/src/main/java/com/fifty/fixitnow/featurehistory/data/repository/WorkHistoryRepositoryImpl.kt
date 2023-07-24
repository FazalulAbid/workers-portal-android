package com.fifty.fixitnow.featurehistory.data.repository

import android.util.Log
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featurehistory.domain.repository.WorkHistoryRepository
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService

class WorkHistoryRepositoryImpl(
    private val api: WorkProposalApiService
) : WorkHistoryRepository {

    override suspend fun getWorkHistoryReport(
        fromDate: Long,
        toDate: Long,
        isWorkHistoryNeeded: Boolean,
        isHiringHistoryNeeded: Boolean,
        isPendingWorksNeeded: Boolean,
        isCancelledWorksNeeded: Boolean,
        isCompletedWorksNeeded: Boolean,
        page: Int
    ): SimpleResource {
        val result = api.getWorkHistoryReport(
            fromDate = fromDate,
            toDate = toDate,
            isWorkHistoryNeeded = isWorkHistoryNeeded,
            isHiringHistoryNeeded = isHiringHistoryNeeded,
            isPendingWorksNeeded = isPendingWorksNeeded,
            isCancelledWorksNeeded = isCancelledWorksNeeded,
            isCompletedWorksNeeded = isCompletedWorksNeeded,
            page = page,
            pageSize = Constants.DEFAULT_PAGINATION_SIZE
        )
        Log.d("Hello", "getWorkHistoryReport: ${result.body()}")
        return Resource.Success(Unit)
    }
}