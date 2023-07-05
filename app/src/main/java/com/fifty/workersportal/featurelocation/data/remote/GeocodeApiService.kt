package com.fifty.workersportal.featurelocation.data.remote

import com.fifty.workersportal.featurelocation.data.remote.dto.ReverseGeocodeDisplayAddressDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeApiService {

    @GET("https://geocode.maps.co/reverse")
    suspend fun reverseGeocodeForDisplayAddress(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<ReverseGeocodeDisplayAddressDto>
}