package com.fifty.fixitnow.featurehistory.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurehistory.data.paging.WorkHistoryPagingSource
import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import com.fifty.fixitnow.featurehistory.domain.repository.WorkHistoryRepository
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService
import kotlinx.coroutines.flow.Flow

class WorkHistoryRepositoryImpl(
    private val api: WorkProposalApiService
) : WorkHistoryRepository {

    override fun getWorkHistoryPaged(
        fromDate: Long,
        toDate: Long,
        isWorkHistoryNeeded: Boolean,
        isHiringHistoryNeeded: Boolean,
        isPendingWorksNeeded: Boolean,
        isCancelledWorksNeeded: Boolean,
        isCompletedWorksNeeded: Boolean
    ): Flow<PagingData<WorkHistory>> {
        val pagingConfig = PagingConfig(pageSize = Constants.DEFAULT_PAGINATION_SIZE)

        return Pager(pagingConfig) {
            WorkHistoryPagingSource(
                api = api,
                fromDate = fromDate,
                toDate = toDate,
                isWorkHistoryNeeded = isWorkHistoryNeeded,
                isHiringHistoryNeeded = isHiringHistoryNeeded,
                isPendingWorksNeeded = isPendingWorksNeeded,
                isCancelledWorksNeeded = isCancelledWorksNeeded,
                isCompletedWorksNeeded = isCompletedWorksNeeded
            )
        }.flow
    }
}