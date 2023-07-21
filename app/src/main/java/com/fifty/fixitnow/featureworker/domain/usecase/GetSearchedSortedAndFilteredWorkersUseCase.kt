package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class GetSearchedSortedAndFilteredWorkersUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(
        page: Int,
        query: String,
        categoryId: String?,
        availabilityCheckDate: Long? = null,
        isFullDay: Boolean? = null,
        isBeforeNoon: Boolean? = null
    ): Resource<List<Worker>> {
        return repository.getSearchedSortedAndFilteredWorkers(
            query = query,
            page = page,
            pageSize = Constants.DEFAULT_PAGINATION_SIZE,
            categoryId = categoryId,
            availabilityCheckDate = availabilityCheckDate,
            isFullDay = isFullDay,
            isBeforeNoon = isBeforeNoon
        )
    }
}