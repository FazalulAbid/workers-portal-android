package com.fifty.workersportal.featureauth.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.data.remote.CountryCodeApiService
import com.fifty.workersportal.featureauth.domain.model.Country
import com.fifty.workersportal.featureauth.domain.repository.CountryCodeRepository
import java.io.IOException

class CountryCodeRepositoryImpl(
    private val api: CountryCodeApiService
) : CountryCodeRepository {

    override suspend fun getCountries(): Resource<List<Country>> {
        return try {
            val countries = api.getCountries().map {
                it.toCountry()
            }
            Resource.Success(countries)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_could_not_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}