package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class GetFavouritesUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(page: Int): Resource<List<Worker>> {
        return repository.getFavourites(page)
    }
}