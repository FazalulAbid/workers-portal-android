package com.fifty.workersportal.featurelocation.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress

interface LocationRepository {

    suspend fun saveAddress(address: LocalAddress): SimpleResource

    suspend fun getAddressesOfUser(userId: String): Resource<List<LocalAddress>>
}