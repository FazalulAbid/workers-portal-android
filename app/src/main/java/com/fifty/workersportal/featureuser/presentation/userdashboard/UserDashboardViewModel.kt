package com.fifty.workersportal.featureuser.presentation.userdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressUseCase
import com.fifty.workersportal.featureuser.domain.usecase.GetDashboardBannersUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSuggestedCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getDashboardBannersUseCase: GetDashboardBannersUseCase,
    private val getCategoriesUseCase: GetSuggestedCategoriesUseCase,
    private val toggleFavouriteWorkerUseCase: ToggleFavouriteWorkerUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase
) : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _state = mutableStateOf(UserDashboardState())
    val state: State<UserDashboardState> = _state

    private val _suggestedCategoriesState = mutableStateOf(SuggestedCategoriesState())
    val suggestedCategoriesState: State<SuggestedCategoriesState> = _suggestedCategoriesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getBanners()
        getSuggestedCategories()
    }

    fun onEvent(event: UserDashboardEvent) {
        when (event) {
            is UserDashboardEvent.ToggleFavouriteWorker -> {
                toggleFavouriteWorker(event.value)
            }

            UserDashboardEvent.UpdateSelectedAddress -> {
                getLocalAddress()
            }
        }
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
                        Screen.SelectLocationScreen.route
                    )
                )
            }
        }
    }

    private fun toggleFavouriteWorker(value: Boolean) {
        viewModelScope.launch {
            when (toggleFavouriteWorkerUseCase("648c4a6a9843bfdb80fb4e90", value)) {
                is Resource.Success -> {

                }

                is Resource.Error -> {

                }
            }
        }
    }

    private fun getSuggestedCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
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
            when (val result = getDashboardBannersUseCase()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        banners = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun getLocalAddress() {
        _state.value = state.value.copy(
            selectedLocalAddress = Session.selectedAddress.value
        )
    }
}