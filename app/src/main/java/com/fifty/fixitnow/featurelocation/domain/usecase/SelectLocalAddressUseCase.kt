package com.fifty.fixitnow.featurelocation.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featurelocation.domain.repository.LocationRepository

class SelectLocalAddressUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(addressId: String): Resource<LocalAddress> {
        return repository.setSelectedAddress(addressId)
    }
}