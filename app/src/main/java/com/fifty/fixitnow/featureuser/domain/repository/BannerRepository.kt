package com.fifty.fixitnow.featureuser.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureuser.domain.model.Banner

interface BannerRepository {

    suspend fun getBanners(): Resource<List<Banner>>
}