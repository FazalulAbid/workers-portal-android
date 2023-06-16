package com.fifty.workersportal.featureworker.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import java.io.IOException

class ReviewAndRatingRepositoryImpl(
    private val api: WorkerApiService
) : ReviewAndRatingRepository {

    override suspend fun postReviewAndRating(reviewAndRating: ReviewAndRating): SimpleResource {
        return try {
            val response = api.postReviewAndRating(reviewAndRating)
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
}