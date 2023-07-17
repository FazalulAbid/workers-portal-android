package com.fifty.fixitnow.featurelocation.util

import com.fifty.fixitnow.core.util.Error

sealed class AddressError : Error() {
    object EmptyAddressTitle : AddressError()
    object EmptyCompleteAddress : AddressError()
    object EmptyCompleteAddressInputTooShort : AddressError()
}