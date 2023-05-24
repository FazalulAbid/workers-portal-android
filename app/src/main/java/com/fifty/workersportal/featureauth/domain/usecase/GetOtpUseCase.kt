package com.fifty.workersportal.featureauth.domain.usecase

import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.CountryCodeRepository
import java.util.regex.Pattern

class GetOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        val pattern = "^\\+$countryCode\\d{1,14}$"
        val regex = Regex(pattern)
        if (phoneNumber.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.this_field_cant_be_empty))
        }
        if (regex.matches("+$countryCode$phoneNumber")) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_valid_phone_number))
        }
        return repository.getOtp(phoneNumber = phoneNumber, countryCode = countryCode)
    }
}