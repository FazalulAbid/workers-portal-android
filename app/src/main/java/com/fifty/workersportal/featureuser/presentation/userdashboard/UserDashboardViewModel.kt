package com.fifty.workersportal.featureuser.presentation.userdashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            val userId = getOwnUserId()
        }
    }
}