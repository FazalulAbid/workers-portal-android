package com.fifty.fixitnow.featurelocation.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featurelocation.domain.model.ReverseGeocodeDisplayAddress
import com.fifty.fixitnow.featurelocation.domain.repository.LocationRepository

class ReverseGeocodeForDisplayAddressUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Resource<ReverseGeocodeDisplayAddress> =
        repository.reverseGeocodeForDisplayAddress(latitude, longitude)
}