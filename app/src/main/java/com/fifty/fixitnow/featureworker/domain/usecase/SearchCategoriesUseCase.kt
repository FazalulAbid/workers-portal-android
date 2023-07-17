package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class SearchCategoriesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(page: Int, searchQuery: String): Resource<List<Category>> =
        repository.getSearchedCategoriesPaged(page, searchQuery)
}