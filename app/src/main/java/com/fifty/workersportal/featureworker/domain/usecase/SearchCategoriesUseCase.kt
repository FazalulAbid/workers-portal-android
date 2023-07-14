package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class SearchCategoriesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(page: Int, searchQuery: String): Resource<List<Category>> =
        repository.getSearchedCategoriesPaged(page, searchQuery)
}