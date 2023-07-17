package com.fifty.fixitnow.featurelocation.presentation.detectcurrentlocation

sealed class DetectCurrentLocationEvent {
    data class SelectSaveAddressAs(val value: String) : DetectCurrentLocationEvent()
    data class EnterAddressTitle(val title: String) : DetectCurrentLocationEvent()
    data class EnterCompleteAddress(val address: String) : DetectCurrentLocationEvent()
    data class EnterFloor(val floor: String) : DetectCurrentLocationEvent()
    data class EnterLandmark(val landmark: String) : DetectCurrentLocationEvent()
    data class SelectCurrentCameraPosition(val lat: Double, val lng: Double) :
        DetectCurrentLocationEvent()

    object SaveAddress : DetectCurrentLocationEvent()
    object CurrentLocation : DetectCurrentLocationEvent()
}
