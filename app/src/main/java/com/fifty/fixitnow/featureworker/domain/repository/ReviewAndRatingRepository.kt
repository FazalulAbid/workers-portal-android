package com.fifty.fixitnow.featureworker.domain.repository

import androidx.paging.PagingData
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.domain.model.RatingsCount
import com.fifty.fixitnow.featureworker.domain.model.ReviewAndRating
import kotlinx.coroutines.flow.Flow

interface ReviewAndRatingRepository {

    fun getReviewsAndRatingsForUserPaged(userId: String): Flow<PagingData<ReviewAndRating>>

    suspend fun getRatingsCount(workerId: String): Resource<RatingsCount>

    suspend fun postReviewAndRating(
        ratedUserId: String,
        review: String,
        rating: Float,
        isWorker: Boolean
    ): SimpleResource
}