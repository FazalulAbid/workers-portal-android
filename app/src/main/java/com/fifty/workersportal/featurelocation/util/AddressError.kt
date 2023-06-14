package com.fifty.workersportal.featurelocation.util

import com.fifty.workersportal.core.util.Error

sealed class AddressError : Error() {
    object EmptyAddressTitle : AddressError()
    object EmptyCompleteAddress : AddressError()
    object EmptyCompleteAddressInputTooShort : AddressError()
}