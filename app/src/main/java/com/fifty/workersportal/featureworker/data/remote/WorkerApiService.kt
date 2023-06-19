package com.fifty.workersportal.featureworker.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.data.remote.dto.CategoryDto
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WorkerApiService {

    @GET("worker/get-worker-categories")
    suspend fun getCategories(): BasicApiResponse<List<CategoryDto>>

    @GET("worker/get-suggested-categories")
    suspend fun getSuggestedCategories(): BasicApiResponse<List<CategoryDto>>

    @POST("rating/add-rating")
    suspend fun postReviewAndRating(
        @Body reviewAndRating: ReviewAndRating
    ): BasicApiResponse<ReviewAndRating>
}