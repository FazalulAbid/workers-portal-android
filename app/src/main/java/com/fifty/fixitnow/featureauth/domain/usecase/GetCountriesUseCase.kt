package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureauth.domain.model.Country
import com.fifty.fixitnow.featureauth.domain.repository.CountryCodeRepository

class GetCountriesUseCase(
    private val repository: CountryCodeRepository
) {

    suspend operator fun invoke(): Resource<List<Country>> {
        return repository.getCountries()
    }
}