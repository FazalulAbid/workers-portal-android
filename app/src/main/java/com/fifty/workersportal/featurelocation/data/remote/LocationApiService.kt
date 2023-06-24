package com.fifty.workersportal.featurelocation.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationApiService {

    @POST("address/add-address")
    suspend fun saveAddress(
        @Body localAddress: LocalAddress
    ): BasicApiResponse<Unit>

    @GET("address/get-all-addresses")
    suspend fun getAddressesOfUser(
        @Query("userId") userId: String
    ): BasicApiResponse<List<LocalAddressDto>>

    @GET("address/get-address")
    suspend fun getAddress(
        @Query("id") addressId: String
    ): BasicApiResponse<LocalAddressDto>

    @GET("address/set-selected-address")
    suspend fun setSelectedAddress(
        @Query("addressId") addressId: String
    ): BasicApiResponse<String>
}