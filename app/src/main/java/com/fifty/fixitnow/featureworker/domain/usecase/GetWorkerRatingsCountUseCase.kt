package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.RatingsCount
import com.fifty.fixitnow.featureworker.domain.repository.ReviewAndRatingRepository

class GetWorkerRatingsCountUseCase(
    private val repository: ReviewAndRatingRepository
) {

    suspend operator fun invoke(workerId: String): Resource<RatingsCount> =
        repository.getRatingsCount(workerId = workerId)
}