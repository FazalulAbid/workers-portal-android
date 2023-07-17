package com.fifty.fixitnow.featureuser.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.featureuser.data.remote.dto.BannerDto
import retrofit2.http.GET

interface BannerApiService {

    @GET("banner/get-banners")
    suspend fun getUserDashboardBanners(): BasicApiResponse<List<BannerDto>>
}