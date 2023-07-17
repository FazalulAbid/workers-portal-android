package com.fifty.fixitnow.featurelocation.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featurelocation.domain.repository.LocationRepository

class GetAddressesOfUserUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(userId: String): Resource<List<LocalAddress>> =
        repository.getAddressesOfUser(userId)
}