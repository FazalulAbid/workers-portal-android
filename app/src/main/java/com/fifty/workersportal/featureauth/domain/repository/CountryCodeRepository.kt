package com.fifty.workersportal.featureauth.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureauth.data.remote.dto.CountryDto
import com.fifty.workersportal.featureauth.domain.model.Country

interface CountryCodeRepository {
    suspend fun getCountries(): Resource<List<Country>>
}