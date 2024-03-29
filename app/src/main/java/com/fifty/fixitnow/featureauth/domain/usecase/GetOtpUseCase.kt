package com.fifty.fixitnow.featureauth.domain.usecase

import android.util.Patterns
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository

class GetOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        countryCode: String,
        phoneNumber: String
    ): SimpleResource {
        val regex = Regex("^\\d{10}$")
        if (phoneNumber.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.error_this_field_cant_be_empty))
        }
        if (!Patterns.PHONE.matcher(phoneNumber.trim())
                .matches() || phoneNumber.trim().length != Constants.DEFAULT_PHONE_NUMBER_LENGTH ||
            !regex.matches(phoneNumber.trim())
        ) {
            return Resource.Error(UiText.StringResource(R.string.please_enter_valid_phone_number))
        }
        return repository.getOtp(phoneNumber = phoneNumber, countryCode = countryCode)
    }
}