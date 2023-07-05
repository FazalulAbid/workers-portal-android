package com.fifty.workersportal.featurelocation.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.model.ReverseGeocodeDisplayAddress

interface LocationRepository {

    suspend fun saveAddress(address: LocalAddress): SimpleResource

    suspend fun getAddressesOfUser(userId: String): Resource<List<LocalAddress>>

    suspend fun getAddress(addressId: String): Resource<LocalAddress>

    suspend fun setSelectedAddress(addressId: String): Resource<LocalAddress>

    suspend fun reverseGeocodeForDisplayAddress(
        lat: Double,
        lon: Double
    ): Resource<ReverseGeocodeDisplayAddress>
}