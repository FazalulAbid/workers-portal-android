package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class GetFavouritesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(page: Int): Resource<List<Worker>> {
        return repository.getFavourites(page)
    }
}