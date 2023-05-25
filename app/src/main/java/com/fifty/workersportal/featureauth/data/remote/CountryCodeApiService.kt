package com.fifty.workersportal.featureauth.data.remote

import com.fifty.workersportal.featureauth.data.remote.dto.CountryDto
import retrofit2.http.GET

interface CountryCodeApiService {

    @GET("all?fields=name,alpha2Code,callingCodes,flags")
    suspend fun getCountries(): List<CountryDto>
}