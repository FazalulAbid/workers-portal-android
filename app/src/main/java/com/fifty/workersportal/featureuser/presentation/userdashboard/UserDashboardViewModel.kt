package com.fifty.workersportal.featureuser.presentation.userdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.domain.usecase.GetDashboardBannersUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSuggestedCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getDashboardBanners: GetDashboardBannersUseCase,
    private val getCategories: GetSuggestedCategoriesUseCase
) : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _bannersState = mutableStateOf(DashboardBannerState())
    val bannersState: State<DashboardBannerState> = _bannersState

    private val _suggestedCategoriesState = mutableStateOf(SuggestedCategoriesState())
    val suggestedCategoriesState: State<SuggestedCategoriesState> = _suggestedCategoriesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getBanners()
        getSuggestedCategories()
    }

    fun dismissPermissionDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        } else {
            viewModelScope.launch {
                _eventFlow.emit(
                    UiEvent.Navigate(
                        Screen.DetectCurrentLocationScreen.route
                    )
                )
            }
        }
    }

    private fun getSuggestedCategories() {
        viewModelScope.launch {
            when (val result = getCategories()) {
                is Resource.Success -> {
                    _suggestedCategoriesState.value = suggestedCategoriesState.value.copy(
                        suggestedCategories = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            when (val result = getDashboardBanners()) {
                is Resource.Success -> {
                    _bannersState.value = _bannersState.value.copy(
                        banners = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {}
            }
        }
    }
}