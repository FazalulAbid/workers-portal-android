package com.fifty.workersportal.featureuser.presentation.edituserprofile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.usecase.GetUserProfileDetailsUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.usecase.SaveUserIdUseCase
import com.fifty.workersportal.featureuser.domain.model.UpdateUserProfileData
import com.fifty.workersportal.featureuser.domain.usecase.UpdateUserProfileUseCase
import com.fifty.workersportal.featureworker.util.ProfileError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserProfileViewModel @Inject constructor(
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val userProfileDetailsUseCase: GetUserProfileDetailsUseCase,
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

    private val _updateUserProfileState = mutableStateOf(UpdateUserProfileState())
    val updateUserProfileState: State<UpdateUserProfileState> = _updateUserProfileState

    private val _errorFlow = MutableSharedFlow<ProfileError>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _onUpdateUserProfile = MutableSharedFlow<Unit>(replay = 1)
    val onUpdateUserProfile = _onUpdateUserProfile.asSharedFlow()

    init {
        viewModelScope.launch {
            val userId = getOwnUserId()
            getUserProfileDetails(userId)
        }
    }

    fun onEvent(event: UpdateUserProfileEvent) {
        when (event) {
            is UpdateUserProfileEvent.CropProfilePicture -> {
                _profileImageUri.value = event.uri
            }

            is UpdateUserProfileEvent.EnterAge -> {
                _ageState.value = _ageState.value.copy(
                    text = event.age
                )
            }

            is UpdateUserProfileEvent.EnterEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }

            is UpdateUserProfileEvent.EnterFirstName -> {
                _firstNameState.value = firstNameState.value.copy(
                    text = event.firstName
                )
            }

            is UpdateUserProfileEvent.EnterLastName -> {
                _lastNameState.value = lastNameState.value.copy(
                    text = event.lastName
                )
            }

            is UpdateUserProfileEvent.GenderSelectedChanged -> {
                _genderState.value = event.gender
            }

            UpdateUserProfileEvent.UpdateUserProfile -> {
                updateUserProfile()
            }
        }
    }

    private suspend fun getUserProfileDetails(userId: String) {
        _updateUserProfileState.value = updateUserProfileState.value.copy(
            isLoading = true,
            loadingText = R.string.fetching_user_data
        )
        when (val result = userProfileDetailsUseCase(userId)) {
            is Resource.Success -> {
                val profile = result.data ?: kotlin.run {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            UiText.StringResource(R.string.oops_couldn_t_load_profile)
                        )
                    )
                    return
                }
                _firstNameState.value = firstNameState.value.copy(
                    text = profile.firstName
                )
                _lastNameState.value = lastNameState.value.copy(
                    text = profile.lastName
                )
                _emailState.value = emailState.value.copy(
                    text = profile.email
                )
                _genderState.value =
                    profile.gender.replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(java.util.Locale.ROOT)
                        else it.toString()
                    }
                _ageState.value = ageState.value.copy(
                    text = profile.age.toString()
                )
                _updateUserProfileState.value = updateUserProfileState.value.copy(
                    isLoading = false,
                    userProfile = profile.toUserProfile()
                )
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                )
                _updateUserProfileState.value = updateUserProfileState.value.copy(
                    isLoading = false,
                )
            }
        }
    }

    private fun updateUserProfile() {
        _updateUserProfileState.value = UpdateUserProfileState(
            isLoading = true,
            loadingText = R.string.updating_user_data
        )
        viewModelScope.launch {
            _firstNameState.value = firstNameState.value.copy(error = null)
            _emailState.value = emailState.value.copy(error = null)
            _ageState.value = ageState.value.copy(error = null)

            val updateUserProfileResult = updateUserProfileUseCase(
                UpdateUserProfileData(
                    userId = getOwnUserId(),
                    firstName = firstNameState.value.text,
                    lastName = lastNameState.value.text,
                    email = emailState.value.text,
                    gender = genderState.value,
                    age = if (_ageState.value.text.isBlank()) 0 else _ageState.value.text.toInt(),
                ),
                profilePictureUri = profileImageUri.value
            )

            if (updateUserProfileResult.firstNameError != null) {
                _errorFlow.emit(
                    updateUserProfileResult.firstNameError
                )
            } else if (updateUserProfileResult.emailError != null) {
                _errorFlow.emit(
                    updateUserProfileResult.emailError
                )
            } else if (updateUserProfileResult.ageError != null) {
                _errorFlow.emit(
                    updateUserProfileResult.ageError
                )
            } else {
                when (updateUserProfileResult.result) {
                    is Resource.Success -> {
                        val profile = updateUserProfileResult.result.data
                        profile?.let {
                            Session.userSession.value = it
                            _onUpdateUserProfile.emit(Unit)
                        }
                        _updateUserProfileState.value = UpdateUserProfileState(isLoading = false)
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.MakeToast(
                                uiText = updateUserProfileResult.result.uiText
                                    ?: UiText.unknownError()
                            )
                        )
                        _updateUserProfileState.value = UpdateUserProfileState(isLoading = false)
                    }

                    null -> {
                        _updateUserProfileState.value = UpdateUserProfileState(isLoading = false)
                    }
                }
            }
        }
    }
}