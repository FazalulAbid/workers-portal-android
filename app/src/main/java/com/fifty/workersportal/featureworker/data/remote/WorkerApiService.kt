package com.fifty.workersportal.featureworker.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureuser.data.remote.FavouriteUpdateRequest
import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto
import com.fifty.workersportal.featureworker.data.remote.dto.CategoryDto
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("profile/open-to-work-on")
    suspend fun openToWorkOn(): BasicApiResponse<String>

    @PATCH("profile/open-to-work-off")
    suspend fun openToWorkOff(): BasicApiResponse<String>

    @POST("favourites/add-to-favourites")
    suspend fun addWorkerToFavourites(
        @Body favouriteUpdateRequest: FavouriteUpdateRequest
    ): BasicApiResponse<String>

    @POST("favourites/add-to-favourites")
    suspend fun removeWorkerFromFavourites(
        @Body favouriteUpdateRequest: FavouriteUpdateRequest
    ): BasicApiResponse<String>
}