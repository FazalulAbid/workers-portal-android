package com.fifty.workersportal.featurelocation.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featurelocation.data.remote.LocationApiService
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository
import java.io.IOException

class LocationRepositoryImpl(
    private val api: LocationApiService
) : LocationRepository {

    override suspend fun saveAddress(address: LocalAddress): SimpleResource {
        return try {
            Resource.Success(Unit)
//            val response = api.saveAddress(address)
//            if (response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { message ->
//                    Resource.Error(UiText.DynamicString(message))
//                } ?: Resource.Error(UiText.unknownError())
//            }
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
}