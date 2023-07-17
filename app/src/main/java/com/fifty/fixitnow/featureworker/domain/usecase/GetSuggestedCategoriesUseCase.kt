package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class GetSuggestedCategoriesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(): Resource<List<Category>> {
        return repository.getSuggestedCategories()
    }
}