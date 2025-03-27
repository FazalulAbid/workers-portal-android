package com.fifty.fixitnow.featurelocation.domain.usecase

import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurelocation.domain.model.LocalAddress
import com.fifty.fixitnow.featurelocation.domain.model.AddressResult
import com.fifty.fixitnow.featurelocation.domain.repository.LocationRepository
import com.fifty.fixitnow.featurelocation.util.AddressError

class SaveAddressUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(address: LocalAddress): AddressResult {
        val titleError = validateTitle(address.title.trim())
        val completeAddressError = validateCompleteAddress(address.completeAddress.trim())

        return if (titleError != null || completeAddressError != null) {
            AddressResult(
                titleError = titleError,
                completeAddressError = completeAddressError
            )
        } else {
            AddressResult(
                result = repository.saveAddress(address)
            )
        }
    }

    private fun validateTitle(title: String): AddressError? {
        return if (title.isBlank()) {
            AddressError.EmptyAddressTitle
        } else null
    }

    private fun validateCompleteAddress(completeAddress: String): AddressError? {
        return if (completeAddress.isBlank()) {
            AddressError.EmptyCompleteAddress
        } else if (completeAddress.length < Constants.MINIMUM_ADDRESS_LENGTH) {
            AddressError.EmptyCompleteAddressInputTooShort
        } else {
            null
        }
    }
}