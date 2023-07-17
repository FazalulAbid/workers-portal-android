package com.fifty.fixitnow.featureauth.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureauth.domain.model.Country

interface CountryCodeRepository {
    suspend fun getCountries(): Resource<List<Country>>
}