package com.fifty.fixitnow.featurelocation.domain.usecase

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class GetCurrentLocationUseCase(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Location? = suspendCancellableCoroutine { continuation ->
        try {
            val locationTask = fusedLocationProviderClient.lastLocation
            locationTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val lastKnownLocation = task.result
                    continuation.resume(lastKnownLocation) {}
                } else {
                    continuation.resume(null) {}
                }
            }
        } catch (e: SecurityException) {
            continuation.resume(null) {}
        }
    }
}