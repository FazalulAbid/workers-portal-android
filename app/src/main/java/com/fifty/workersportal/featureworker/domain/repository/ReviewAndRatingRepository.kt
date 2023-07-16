package com.fifty.workersportal.featureworker.domain.repository

import androidx.paging.PagingData
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.model.RatingsCount
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
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