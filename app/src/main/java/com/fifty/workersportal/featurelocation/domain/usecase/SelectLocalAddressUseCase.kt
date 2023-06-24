package com.fifty.workersportal.featurelocation.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository

class SelectLocalAddressUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(addressId: String): Resource<LocalAddress> {
        return repository.setSelectedAddress(addressId)
    }
}