package com.fifty.workersportal.featurelocation.data.repository

import android.util.Log
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
            val response = api.saveAddress(address)
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

    val dummyAddresses = listOf(
        LocalAddress(
            id = "001",
            title = "Home",
            completeAddress = "123 Main Street",
            floor = null,
            landmark = "Near Park",
            place = null,
            subLocality = "Example Suburb",
            city = "Example City",
            state = "Example State",
            country = "Example Country",
            pin = "12345",
            location = listOf(37.7749, -122.4194)
        ),
        LocalAddress(
            id = "002",
            title = "Work",
            completeAddress = "456 Business Avenue",
            floor = "3rd Floor",
            landmark = "Opposite Mall",
            place = null,
            subLocality = "Business District",
            city = "Example City",
            state = "Example State",
            country = "Example Country",
            pin = "54321",
            location = listOf(40.7128, -74.0060)
        ),
        LocalAddress(
            id = "003",
            title = "Friend's Place",
            completeAddress = "789 Friendship Road",
            floor = null,
            landmark = null,
            place = "Friendship Colony",
            subLocality = "Example Suburb",
            city = "Example City",
            state = "Example State",
            country = "Example Country",
            pin = "67890",
            location = listOf(51.5074, -0.1278)
        )
    )

    override suspend fun getAddressesOfUser(userId: String): Resource<List<LocalAddress>> {
        return try {
//            val response = api.getAddressesOfUser(userId)
//            if (response.successful) {
//                Resource.Success(data = response.data?.map { it.toLocalAddress() })
//            } else {
//                response.message?.let { message ->
//                    Resource.Error(UiText.DynamicString(message))
//                } ?: Resource.Error(UiText.unknownError())
//            }
            Resource.Success(data = dummyAddresses)
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