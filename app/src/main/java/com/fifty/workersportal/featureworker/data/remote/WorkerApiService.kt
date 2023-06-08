package com.fifty.workersportal.featureworker.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureworker.data.remote.dto.CategoryDto
import retrofit2.http.GET

interface WorkerApiService {

    @GET("worker/get-worker-categories")
    suspend fun getCategories(): BasicApiResponse<List<CategoryDto>>

    @GET("worker/get-suggested-categories")
    suspend fun getSuggestedCategories(): BasicApiResponse<List<CategoryDto>>
}