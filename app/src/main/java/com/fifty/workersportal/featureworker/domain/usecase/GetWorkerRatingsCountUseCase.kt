package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.RatingsCount
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository

class GetWorkerRatingsCountUseCase(
    private val repository: ReviewAndRatingRepository
) {

    suspend operator fun invoke(workerId: String): Resource<RatingsCount> =
        repository.getRatingsCount(workerId = workerId)
}