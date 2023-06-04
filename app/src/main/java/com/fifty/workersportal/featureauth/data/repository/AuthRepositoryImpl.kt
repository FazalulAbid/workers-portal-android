package com.fifty.workersportal.featureauth.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.data.util.ApiConstants.ACCESS_TOKEN_KEY
import com.fifty.workersportal.core.data.util.ApiConstants.REFRESH_TOKEN_KEY
import com.fifty.workersportal.core.data.util.Session
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.utils.SessionManager
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.remote.AuthenticateApiService
import com.fifty.workersportal.featureauth.data.remote.request.SendOtpRequest
import com.fifty.workersportal.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val authenticateApi: AuthenticateApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        return try {
            val response = api.getOtp(
                SendOtpRequest(
                    countryCode = countryCode,
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

    override suspend fun verifyOtp(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): SimpleResource {
        return try {
            val response = api.verifyOtp(
                VerifyOtpRequest(
                    countryCode = countryCode,
                    phoneNumber = phoneNumber,
                    otpCode = otpCode
                )
            )
            if (response.isSuccessful && response.body()?.successful == true) {
                val accessToken = response.headers()[ACCESS_TOKEN_KEY]
                val refreshToken = response.headers()[REFRESH_TOKEN_KEY]
                val userId = response.body()?.data?.id
                accessToken?.let { token ->
                    sessionManager.saveAccessToken(token)
                }
                refreshToken?.let { token ->
                    sessionManager.saveRefreshToken(token)
                }
                userId?.let {
                    sessionManager.saveUserId(it)
                }
                Resource.Success(Unit)
            } else {
                response.body()?.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
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

    override suspend fun authenticate(): SimpleResource {
        return try {
            authenticateApi.authenticate().body()?.let { response ->
                if (response.successful) {
                    val user = response.data?.toUser()
                    user?.let {
//                        Session.user = it
                        Resource.Success(Unit)
                    } ?: return Resource.Error(UiText.unknownError())
                } else {
                    response.message?.let { message ->
                        Resource.Error(UiText.DynamicString(message))
                    } ?: Resource.Error(UiText.unknownError())
                }
            } ?: Resource.Error(UiText.unknownError())
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