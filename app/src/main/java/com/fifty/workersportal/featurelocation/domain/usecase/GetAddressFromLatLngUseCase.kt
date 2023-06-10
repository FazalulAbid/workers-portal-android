package com.fifty.workersportal.featurelocation.domain.usecase

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

@Suppress("DEPRECATION")
class GetAddressFromLatLngUseCase(
    private val geocoder: Geocoder
) {
    operator fun invoke(latLng: LatLng): Address? {
        try {
            val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            return list?.first()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}