package com.fifty.fixitnow.featureworker.domain.usecase

import androidx.paging.PagingData
import com.fifty.fixitnow.featureworker.domain.model.ReviewAndRating
import com.fifty.fixitnow.featureworker.domain.repository.ReviewAndRatingRepository
import kotlinx.coroutines.flow.Flow

class GetReviewAndRatingUseCase(
    private val repository: ReviewAndRatingRepository
) {

    operator fun invoke(userId: String): Flow<PagingData<ReviewAndRating>> =
        repository.getReviewsAndRatingsForUserPaged(userId)
}