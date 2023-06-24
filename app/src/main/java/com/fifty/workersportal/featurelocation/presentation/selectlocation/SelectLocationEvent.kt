package com.fifty.workersportal.featurelocation.presentation.selectlocation

sealed class SelectLocationEvent {
    data class SelectLocalAddress(val addressId: String) : SelectLocationEvent()
    object RefreshAddressList : SelectLocationEvent()
}
