package com.fifty.workersportal.featureworker.domain.usecase

import androidx.paging.PagingData
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import kotlinx.coroutines.flow.Flow

class SearchCategoriesUseCase(
    private val repository: WorkerRepository
) {

    operator fun invoke(searchQuery: String): Flow<PagingData<Category>> =
        repository.getSearchedCategoriesPaged(searchQuery)
}