package com.fifty.workersportal.featureworker.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.Category
import retrofit2.HttpException
import java.io.IOException

class CategorySource(
    private val api: WorkerApiService,
    private val searchKey: String
) : PagingSource<Int, Category>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        return try {
            val nextPage = params.key ?: currentPage
            val categories = api.searchCategory(
                page = nextPage,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE,
                searchQuery = searchKey
            ).data ?: emptyList()

            Log.d("Hello", "load: $categories")
            LoadResult.Page(
                data = categories.map { it.toCategory() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (categories.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition
    }

    sealed class Source {
        data class SearchCategories(val searchKey: String) : Source()
    }
}