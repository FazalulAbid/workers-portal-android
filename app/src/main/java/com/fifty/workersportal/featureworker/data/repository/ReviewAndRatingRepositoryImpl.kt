package com.fifty.workersportal.featureworker.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.data.paging.ReviewAndRatingSource
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.remote.request.ReviewAndRatingRequest
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class ReviewAndRatingRepositoryImpl(
    private val api: WorkerApiService
) : ReviewAndRatingRepository {

    override suspend fun postReviewAndRating(
        ratedUserId: String,
        review: String,
        rating: Float,
        isWorker: Boolean
    ): SimpleResource {
        return try {
            val response = api.postReviewAndRating(
                ReviewAndRatingRequest(
                    review = review,
                    rating = rating,
                    ratedUserId = ratedUserId,
                    isWorker = isWorker
                )
            )
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }

    override fun getReviewsAndRatingsForUserPaged(userId: String): Flow<PagingData<ReviewAndRating>> {
        val pagingConfig = PagingConfig(pageSize = Constants.DEFAULT_PAGINATION_SIZE)

        return Pager(pagingConfig) {
            ReviewAndRatingSource(api, userId)
        }.flow
    }
}