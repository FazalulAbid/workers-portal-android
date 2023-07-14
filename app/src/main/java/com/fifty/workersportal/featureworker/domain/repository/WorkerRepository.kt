package com.fifty.workersportal.featureworker.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.Worker

interface WorkerRepository {

    suspend fun getSearchedCategoriesPaged(page: Int, searchQuery: String): Resource<List<Category>>

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getSuggestedCategories(): Resource<List<Category>>

    suspend fun toggleOpenToWork(value: Boolean): SimpleResource

    suspend fun toggleFavouriteWorker(userId: String, isFavourite: Boolean): SimpleResource

    suspend fun getSearchedSortedAndFilteredWorkers(
        query: String,
        page: Int,
        pageSize: Int,
        categoryId: String?
    ): Resource<List<Worker>>

    suspend fun getWorkerDetails(workerId: String): Resource<Worker>
}