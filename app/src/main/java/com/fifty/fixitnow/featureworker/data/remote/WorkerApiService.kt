package com.fifty.fixitnow.featureworker.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.featureuser.data.remote.FavouriteUpdateRequest
import com.fifty.fixitnow.featureworker.data.remote.dto.CategoryDto
import com.fifty.fixitnow.featureworker.data.remote.dto.RatingCountDto
import com.fifty.fixitnow.featureworker.data.remote.dto.ReviewAndRatingDto
import com.fifty.fixitnow.featureworker.data.remote.dto.SampleWorkDto
import com.fifty.fixitnow.featureworker.data.remote.dto.WorkerDto
import com.fifty.fixitnow.featureworker.data.remote.request.ReviewAndRatingRequest
import com.fifty.fixitnow.featureworker.domain.model.ReviewAndRating
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface WorkerApiService {

    @GET("/api/worker/get-workers-list")
    suspend fun getSearchedSortedAndFilteredWorkers(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("query") query: String,
        @Query("category") category: String?
    ): BasicApiResponse<List<WorkerDto>>

    @GET("/api/worker/get-worker-details")
    suspend fun getWorkerDetails(
        @Query("id") workerId: String
    ): BasicApiResponse<WorkerDto>

    @GET("worker/get-worker-categories")
    suspend fun getCategories(): BasicApiResponse<List<CategoryDto>>

    @GET("worker/category-search")
    suspend fun searchCategory(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("key") searchQuery: String
    ): BasicApiResponse<List<CategoryDto>>

    @GET("worker/get-suggested-categories")
    suspend fun getSuggestedCategories(): BasicApiResponse<List<CategoryDto>>

    @POST("rating/add-rating")
    suspend fun postReviewAndRating(
        @Body reviewAndRating: ReviewAndRatingRequest
    ): BasicApiResponse<ReviewAndRating>

    @PATCH("profile/open-to-work-on")
    suspend fun openToWorkOn(): BasicApiResponse<String>

    @PATCH("profile/open-to-work-off")
    suspend fun openToWorkOff(): BasicApiResponse<String>

    @POST("favourites/add-to-favourites")
    suspend fun addWorkerToFavourites(
        @Body favouriteUpdateRequest: FavouriteUpdateRequest
    ): BasicApiResponse<String>

    @DELETE("favourites/remove-from-favourites")
    suspend fun removeWorkerFromFavourites(
        @Query("removedUserId") userId: String
    ): BasicApiResponse<String>

    @GET("favourites/get-favourites")
    suspend fun getFavourites(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): BasicApiResponse<List<WorkerDto>>

    @Multipart
    @POST("sample-work/add-sample-work")
    suspend fun postSampleWork(
        @Part imageUrl: MultipartBody.Part?,
        @Part sampleWorkRequest: MultipartBody.Part
    ): BasicApiResponse<SampleWorkDto>

    @GET("sample-work/get-sample-works")
    suspend fun getSampleWorks(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("id") userId: String
    ): BasicApiResponse<List<SampleWorkDto>>

    @GET("sample-work/get-sample-work")
    suspend fun getSampleWork(
        @Query("id") sampleWorkId: String
    ): BasicApiResponse<SampleWorkDto>

    @DELETE("sample-work/delete-sample-work")
    suspend fun deleteSampleWork(
        @Query("id") sampleWorkId: String
    ): BasicApiResponse<String>

    @GET("rating/get-ratings")
    suspend fun getWorkerReviewAndRatings(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("ratedUserId") userId: String
    ): BasicApiResponse<List<ReviewAndRatingDto>>

    @GET("rating/get-ratings-list")
    suspend fun getWorkerRatingsList(
        @Query("id") workerId: String
    ): BasicApiResponse<RatingCountDto>
}