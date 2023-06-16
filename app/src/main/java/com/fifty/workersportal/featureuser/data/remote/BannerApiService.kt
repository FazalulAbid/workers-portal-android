package com.fifty.workersportal.featureuser.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureuser.data.remote.dto.BannerDto
import com.fifty.workersportal.featureuser.domain.model.Banner
import retrofit2.http.GET

interface BannerApiService {

    @GET("banner/get-banners")
    suspend fun getUserDashboardBanners(): BasicApiResponse<List<BannerDto>>
}