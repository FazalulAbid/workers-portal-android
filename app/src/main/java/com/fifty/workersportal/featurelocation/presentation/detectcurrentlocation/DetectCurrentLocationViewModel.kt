package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetectCurrentLocationViewModel @Inject constructor(

) : ViewModel() {

    private val _state = mutableStateOf(DetectCurrentLocationState())
    val state: State<DetectCurrentLocationState> = _state

    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = state.value.copy(
                        lastKnowLocation = task.result
                    )
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}