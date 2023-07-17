package com.fifty.fixitnow.featureuser.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureuser.domain.model.Banner
import com.fifty.fixitnow.featureuser.domain.repository.BannerRepository

class GetDashboardBannersUseCase(
    private val repository: BannerRepository
) {

    suspend operator fun invoke(): Resource<List<Banner>> =
        repository.getBanners()
}