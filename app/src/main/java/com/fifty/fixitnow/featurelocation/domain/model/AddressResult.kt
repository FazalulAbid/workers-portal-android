package com.fifty.fixitnow.featurelocation.domain.model

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featurelocation.util.AddressError

data class AddressResult(
    val titleError: AddressError? = null,
    val completeAddressError: AddressError? = null,
    val result: SimpleResource? = null
)
