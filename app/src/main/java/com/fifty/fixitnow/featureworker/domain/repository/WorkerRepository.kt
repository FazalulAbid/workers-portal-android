package com.fifty.fixitnow.featureworker.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.model.Worker

interface WorkerRepository {

    suspend fun getSearchedCategoriesPaged(page: Int, searchQuery: String): Resource<List<Category>>

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getSuggestedCategories(): Resource<List<Category>>

    suspend fun toggleOpenToWork(value: Boolean): SimpleResource

    suspend fun toggleFavouriteWorker(userId: String, isFavourite: Boolean): SimpleResource

    suspend fun getFavourites(page: Int): Resource<List<Worker>>

    suspend fun getSearchedSortedAndFilteredWorkers(
        query: String,
        page: Int,
        pageSize: Int,
        categoryId: String?,
        rating4PlusFilter: Boolean?,
        previouslyHiredFilter: Boolean?,
        sortFlag: Int?,
        availabilityCheckDate: Long?,
        isFullDay: Boolean?,
        isBeforeNoon: Boolean?
    ): Resource<List<Worker>>

    suspend fun getWorkerDetails(workerId: String): Resource<Worker>
}