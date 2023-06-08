package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class GetSuggestedCategoriesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(): Resource<List<Category>> {
        return repository.getSuggestedCategories()
    }
}