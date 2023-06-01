package com.fifty.workersportal.featureauth.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureauth.utils.TokenManager
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.remote.request.SendOtpRequest
import com.fifty.workersportal.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    val TAG = "AuthRepositoryImpl"

    override suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        return try {
            Log.d(TAG, "getOtp: getOtp function called!")
            val response = api.getOtp(
                SendOtpRequest(
                    countryCode = countryCode,
                    phoneNumber = phoneNumber
                )
            )
            Log.d(TAG, "getOtp: Got response $response")
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
            Log.d(TAG, "verifyOtp: ${response.body()?.data}")
            if (response.isSuccessful && response.body()?.successful == true) {
                val accessToken = response.headers()["accessToken"]
                val refreshToken = response.headers()["refreshToken"]
                val userId = response.body()?.data?.id
                accessToken?.let { token ->
                    tokenManager.saveAccessToken(token)
                }
                refreshToken?.let { token ->
                    tokenManager.saveRefreshToken(token)
                }
                userId?.let {
                    tokenManager.saveUserId(it)
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
}