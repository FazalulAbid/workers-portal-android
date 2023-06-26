package com.fifty.workersportal.featureuser.presentation.userprofile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.usecase.LogoutUseCase
import com.fifty.workersportal.featureuser.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logout: LogoutUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserProfileState())
    val state: State<UserProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getUserProfile(null)
    }

    fun onEvent(event: UserProfileEvent) {
        when (event) {
            UserProfileEvent.Logout -> {
                viewModelScope.launch {
                    when (logout()) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.OnLogout
                            )
                        }

                        is Resource.Error -> Unit
                    }
                }
            }

            UserProfileEvent.UserProfileUpdated -> {
                getUserProfile(null)
            }
        }
    }

    private fun getUserProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = getUserProfileUseCase(
                userId = userId ?: getOwnUserIdUseCase()
            )) {
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                    )
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        userProfile = result.data,
                        isLoading = false
                    )
                }
            }
        }
    }
}