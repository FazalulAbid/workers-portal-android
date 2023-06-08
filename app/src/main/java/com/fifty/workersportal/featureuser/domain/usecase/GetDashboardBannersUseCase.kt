package com.fifty.workersportal.featureuser.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.domain.model.Banner
import com.fifty.workersportal.featureuser.domain.repository.BannerRepository

class GetDashboardBannersUseCase(
    private val repository: BannerRepository
) {

    suspend operator fun invoke(): Resource<List<Banner>> =
        repository.getBanners()
}