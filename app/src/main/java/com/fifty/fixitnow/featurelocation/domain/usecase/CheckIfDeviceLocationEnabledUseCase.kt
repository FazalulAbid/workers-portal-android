package com.fifty.fixitnow.featurelocation.domain.usecase

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient

class CheckIfDeviceLocationEnabledUseCase(
    private val locationSettingsClient: SettingsClient
) {

    operator fun invoke(): Boolean {
        return try {
            val locationRequest =
                LocationRequest
                    .Builder(
                        Priority.PRIORITY_HIGH_ACCURACY, 100
                    )
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(3000)
                    .setMaxUpdateDelayMillis(100)
                    .build()

            val locationSettingsResponse = locationSettingsClient.checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .build()
            )

            locationSettingsResponse.addOnSuccessListener {
                (it.locationSettingsStates?.isLocationUsable ?: false &&
                        it.locationSettingsStates?.isLocationPresent ?: false)
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}