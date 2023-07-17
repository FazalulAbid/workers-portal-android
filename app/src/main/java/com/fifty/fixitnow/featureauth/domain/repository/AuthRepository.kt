package com.fifty.fixitnow.featureauth.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureauth.domain.model.OtpVerification
import com.fifty.fixitnow.featureuser.domain.model.UserProfile

interface AuthRepository {

    suspend fun getOtp(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource

    suspend fun verifyOtp(
        countryCode: String,
        phoneNumber: String,
        otpCode: String
    ): Resource<OtpVerification>

    suspend fun authenticate(): Resource<UserProfile>
}