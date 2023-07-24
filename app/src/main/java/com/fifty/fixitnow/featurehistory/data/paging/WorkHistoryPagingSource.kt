package com.fifty.fixitnow.featurehistory.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService

class WorkHistoryPagingSource(
    private val api: WorkProposalApiService,
    val fromDate: Long,
    val toDate: Long,
    val isWorkHistoryNeeded: Boolean,
    val isHiringHistoryNeeded: Boolean,
    val isPendingWorksNeeded: Boolean,
    val isCancelledWorksNeeded: Boolean,
    val isCompletedWorksNeeded: Boolean
) : PagingSource<Int, String>() {

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        TODO("Not yet implemented")
    }
}