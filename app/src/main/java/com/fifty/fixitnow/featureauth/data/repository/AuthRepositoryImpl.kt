package com.fifty.fixitnow.featureauth.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.data.util.ApiConstants.ACCESS_TOKEN_KEY
import com.fifty.fixitnow.core.data.util.ApiConstants.REFRESH_TOKEN_KEY
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.data.remote.AuthApiService
import com.fifty.fixitnow.featureauth.data.remote.AuthenticateApiService
import com.fifty.fixitnow.featureauth.data.remote.request.SendOtpRequest
import com.fifty.fixitnow.featureauth.data.remote.request.VerifyOtpRequest
import com.fifty.fixitnow.featureauth.domain.model.OtpVerification
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository
import com.fifty.fixitnow.featureuser.domain.model.UserProfile
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val authenticateApi: AuthenticateApiService
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
    ): Resource<OtpVerification> {
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
                val user = response.body()?.data
                return if (accessToken != null && refreshToken != null && user != null) {
                    Resource.Success(
                        data = OtpVerification(
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            user = user
                        )
                    )
                } else Resource.Error(UiText.unknownError())
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


    override suspend fun authenticate(): Resource<UserProfile> {
        return try {
            authenticateApi.authenticate().body()?.let { response ->
                if (response.successful) {
                    val user = response.data?.toProfile()?.toUserProfile()
                    user?.let {
                        Resource.Success(data = user)
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

    override suspend fun googleSignIn(token: String): Resource<OtpVerification> {
        return try {
            val response = api.googleSignIn(token)
            if (response.isSuccessful && response.body()?.successful == true) {
                val accessToken = response.headers()[ACCESS_TOKEN_KEY]
                val refreshToken = response.headers()[REFRESH_TOKEN_KEY]
                val user = response.body()?.data
                return if (accessToken != null && refreshToken != null && user != null) {
                    Resource.Success(
                        data = OtpVerification(
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            user = user
                        )
                    )
                } else Resource.Error(UiText.unknownError())
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