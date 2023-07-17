package com.fifty.fixitnow.featurelocation.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featurelocation.domain.model.ReverseGeocodeDisplayAddress

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