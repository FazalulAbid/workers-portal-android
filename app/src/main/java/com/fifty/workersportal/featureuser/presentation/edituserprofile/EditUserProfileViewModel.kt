package com.fifty.workersportal.featureuser.presentation.edituserprofile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(

) : ViewModel() {

    private val _firstNameState = mutableStateOf(StandardTextFieldState())
    val firstNameState: State<StandardTextFieldState> = _firstNameState

    private val _lastNameState = mutableStateOf(StandardTextFieldState())
    val lastNameState: State<StandardTextFieldState> = _lastNameState

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _genderState = mutableStateOf(Constants.genderOptions[0])
    val genderState: State<String> = _genderState

    private val _ageState = mutableStateOf(StandardTextFieldState())
    val ageState: State<StandardTextFieldState> = _ageState

    private val _profileImageUri = mutableStateOf<Uri?>(null)
    val profileImageUri: State<Uri?> = _profileImageUri

    private val _editUserProfileState = mutableStateOf(EditUserProfileState())
    val editUserProfileState: State<EditUserProfileState> = _editUserProfileState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getUserProfileDetails()
    }

    fun onEvent(event: EditUserProfileEvent) {
        when (event) {
            is EditUserProfileEvent.CropProfilePicture -> {
                _profileImageUri.value = event.uri
            }

            is EditUserProfileEvent.EnterAge -> {
                _ageState.value = _ageState.value.copy(
                    text = event.age
                )
            }

            is EditUserProfileEvent.EnterEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }

            is EditUserProfileEvent.EnterFirstName -> {
                _firstNameState.value = firstNameState.value.copy(
                    text = event.firstName
                )
            }

            is EditUserProfileEvent.EnterLastName -> {
                _lastNameState.value = lastNameState.value.copy(
                    text = event.lastName
                )
            }

            is EditUserProfileEvent.GenderSelectedChanged -> {
                _genderState.value = event.gender
            }

            EditUserProfileEvent.UpdateUserProfile -> {
                updateUserProfile()
            }
        }
    }

    private fun getUserProfileDetails() {

    }

    private fun updateUserProfile() {

    }
}