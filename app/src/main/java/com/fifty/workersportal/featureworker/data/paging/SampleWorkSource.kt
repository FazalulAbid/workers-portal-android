package com.fifty.workersportal.featureworker.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import retrofit2.HttpException
import java.io.IOException

class SampleWorkSource(
    private val api: WorkerApiService,
    private val userId: String
) : PagingSource<Int, SampleWork>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SampleWork> {
        return try {
            val nextPage = params.key ?: currentPage
            val sampleWorks = api.getSampleWorks(
                page = nextPage,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE,
                userId = userId
            ).data ?: emptyList()

            LoadResult.Page(
                data = sampleWorks.map { it.toSampleWork() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (sampleWorks.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SampleWork>): Int? {
        return state.anchorPosition
    }
}