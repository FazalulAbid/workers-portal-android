package com.fifty.workersportal.featureauth.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.data.remote.AuthApi
import com.fifty.workersportal.featureauth.data.remote.request.AuthRequest
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        return try {
            val response = api.getOtp(
                AuthRequest(
                    countryCode = countryCode,
                    otp = "",
                    phoneNumber = phoneNumber
                )
            )
            if (response.successful) {
                Resource.Success(Unit)
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
}