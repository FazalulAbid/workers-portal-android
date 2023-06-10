package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

sealed class DetectCurrentLocationEvent {
    object CurrentLocation : DetectCurrentLocationEvent()
}
