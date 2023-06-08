package com.fifty.workersportal.featureuser.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.Banner

interface BannerRepository {

    suspend fun getBanners(): Resource<List<Banner>>
}