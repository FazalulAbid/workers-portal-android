package com.fifty.fixitnow.featurehistory.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurehistory.domain.model.WorkHistory
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService
import retrofit2.HttpException
import java.io.IOException

class WorkHistoryPagingSource(
    private val api: WorkProposalApiService,
    private val fromDate: Long,
    private val toDate: Long,
    private val isWorkHistoryNeeded: Boolean,
    private val isHiringHistoryNeeded: Boolean,
    private val isPendingWorksNeeded: Boolean,
    private val isCancelledWorksNeeded: Boolean,
    private val isCompletedWorksNeeded: Boolean
) : PagingSource<Int, WorkHistory>() {

    private var currentPage = 0

    override suspend fun load(params: PagingSource.LoadParams<Int>): LoadResult<Int, WorkHistory> {
        return try {
            val nextPage = params.key ?: currentPage
            val workHistory = api.getWorkHistoryReport(
                page = nextPage,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE,
                fromDate = fromDate,
                toDate = toDate,
                isWorkHistoryNeeded = isWorkHistoryNeeded,
                isHiringHistoryNeeded = isHiringHistoryNeeded,
                isPendingWorksNeeded = isPendingWorksNeeded,
                isCancelledWorksNeeded = isCancelledWorksNeeded,
                isCompletedWorksNeeded = isCompletedWorksNeeded
            ).data ?: emptyList()

            LoadResult.Page(
                data = workHistory.map { it.toWorkHistory() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (workHistory.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WorkHistory>): Int? {
        return state.anchorPosition
    }
}