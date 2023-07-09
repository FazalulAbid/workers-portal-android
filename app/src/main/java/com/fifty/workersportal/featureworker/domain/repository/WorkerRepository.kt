package com.fifty.workersportal.featureworker.domain.repository

import androidx.paging.PagingData
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.Worker
import kotlinx.coroutines.flow.Flow

interface WorkerRepository {

    fun getSearchedCategoriesPaged(searchQuery: String): Flow<PagingData<Category>>

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getSuggestedCategories(): Resource<List<Category>>

    suspend fun toggleOpenToWork(value: Boolean): SimpleResource

    suspend fun toggleFavouriteWorker(userId: String, value: Boolean): SimpleResource

    suspend fun getSearchedSortedAndFilteredWorkers(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Worker>>
}