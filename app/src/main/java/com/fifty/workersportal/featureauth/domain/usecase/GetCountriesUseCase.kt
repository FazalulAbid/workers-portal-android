package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureauth.domain.model.Country
import com.fifty.workersportal.featureauth.domain.repository.CountryCodeRepository

class GetCountriesUseCase(
    private val repository: CountryCodeRepository
) {

    suspend operator fun invoke(): Resource<List<Country>> {
        return repository.getCountries()
    }
}