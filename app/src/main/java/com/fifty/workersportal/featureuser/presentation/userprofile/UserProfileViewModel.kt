package com.fifty.workersportal.featureuser.presentation.userprofile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val logout: LogoutUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserProfileState())
    val state: State<UserProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
        }
    }
}