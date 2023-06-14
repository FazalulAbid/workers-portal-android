package com.fifty.workersportal.featurelocation.domain.model

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featurelocation.util.AddressError

data class AddressResult(
    val titleError: AddressError? = null,
    val completeAddressError: AddressError? = null,
    val result: SimpleResource? = null
)
