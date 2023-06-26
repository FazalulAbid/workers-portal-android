package com.fifty.workersportal.featureworker.domain.usecase

import androidx.paging.PagingData
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import kotlinx.coroutines.flow.Flow

class GetReviewAndRatingUseCase(
    private val repository: ReviewAndRatingRepository
) {

    operator fun invoke(userId: String): Flow<PagingData<ReviewAndRating>> =
        repository.getReviewsAndRatingsForUserPaged(userId)
}