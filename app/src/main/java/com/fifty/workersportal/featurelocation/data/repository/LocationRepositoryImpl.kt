package com.fifty.workersportal.featurelocation.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featurelocation.data.remote.GeocodeApiService
import com.fifty.workersportal.featurelocation.data.remote.LocationApiService
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.model.ReverseGeocodeDisplayAddress
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository
import java.io.IOException

class LocationRepositoryImpl(
    private val locationApi: LocationApiService,
    private val geocodeApi: GeocodeApiService
) : LocationRepository {

    override suspend fun saveAddress(address: LocalAddress): SimpleResource {
        return try {
            val response = locationApi.saveAddress(address)
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }

    override suspend fun getAddressesOfUser(userId: String): Resource<List<LocalAddress>> {
        return try {
            val response = locationApi.getAddressesOfUser(userId)
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toLocalAddress() })
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
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

    override suspend fun getAddress(addressId: String): Resource<LocalAddress> {
        return try {
            val response = locationApi.getAddress(addressId)
            if (response.successful) {
                Resource.Success(data = response.data?.toLocalAddress())
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
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

    override suspend fun setSelectedAddress(addressId: String): Resource<LocalAddress> {
        return try {
            val response = locationApi.setSelectedAddress(addressId)
            if (response.successful) {
                Resource.Success(data = response.data?.toLocalAddress())
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
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

    override suspend fun reverseGeocodeForDisplayAddress(
        lat: Double,
        lon: Double
    ): Resource<ReverseGeocodeDisplayAddress> {
        return try {
            val response =
                geocodeApi.reverseGeocodeForDisplayAddress(latitude = lat, longitude = lon)
            if (response.isSuccessful) {
                Resource.Success(data = response.body()?.toReverseGeocodeDisplayAddress())
            } else {
                Resource.Error(UiText.DynamicString(response.message()))
            }
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