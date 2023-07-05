package com.fifty.workersportal.featurelocation.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featurelocation.domain.model.ReverseGeocodeDisplayAddress
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository

class ReverseGeocodeForDisplayAddressUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Resource<ReverseGeocodeDisplayAddress> =
        repository.reverseGeocodeForDisplayAddress(latitude, longitude)
}