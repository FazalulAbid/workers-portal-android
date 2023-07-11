package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class GetSearchedSortedAndFilteredWorkersUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(page: Int, query: String, categoryId: String?): Resource<List<Worker>> {
        return repository.getSearchedSortedAndFilteredWorkers(
            query = query,
            page = page,
            pageSize = Constants.DEFAULT_PAGINATION_SIZE,
            categoryId = categoryId
        )
    }
}