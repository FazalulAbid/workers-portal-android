package com.fifty.workersportal.featureauth.domain.usecase

import android.util.Patterns
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository

class GetOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        if (phoneNumber.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.this_field_cant_be_empty))
        }
        if (!Patterns.PHONE.matcher(phoneNumber.trim()).matches()) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_valid_phone_number))
        }
        return repository.getOtp(phoneNumber = phoneNumber, countryCode = countryCode)
    }
}