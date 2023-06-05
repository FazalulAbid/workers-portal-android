package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class RegisterAsWorkerViewModel @Inject constructor(

) : ViewModel() {

    private val _firstNameState = mutableStateOf(StandardTextFieldState())
    val firstNameState: State<StandardTextFieldState> = _firstNameState

    private val _lastNameState = mutableStateOf(StandardTextFieldState())
    val lastNameState: State<StandardTextFieldState> = _lastNameState

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterAsWorkerEvent) {
        when (event) {
            is RegisterAsWorkerEvent.ToggleOpenToWork -> {

            }

            is RegisterAsWorkerEvent.EnterAge -> {

            }

            is RegisterAsWorkerEvent.EnterBio -> {

            }

            is RegisterAsWorkerEvent.EnterEmail -> {

            }

            is RegisterAsWorkerEvent.EnterFirstName -> {

            }

            is RegisterAsWorkerEvent.EnterGender -> {

            }

            is RegisterAsWorkerEvent.EnterLastName -> {

            }


        }
    }
}