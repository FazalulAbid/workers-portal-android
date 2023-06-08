package com.fifty.workersportal.featureworker.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureworker.data.remote.dto.CategoryDto
import retrofit2.http.GET

interface WorkerApiService {

    @GET("worker/get-worker-categories")
    suspend fun getWorkerCategories(): BasicApiResponse<List<CategoryDto>>
}