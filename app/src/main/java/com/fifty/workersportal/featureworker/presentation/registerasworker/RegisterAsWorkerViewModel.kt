package com.fifty.workersportal.featureworker.presentation.registerasworker

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants.genderOptions
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.fifty.workersportal.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.SetSkillSelectedUseCase
import com.fifty.workersportal.featureworker.domain.usecase.UpdateUserAsWorkerUseCase
import com.fifty.workersportal.featureworker.util.ProfileError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterAsWorkerViewModel @Inject constructor(
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val getCategories: GetCategoriesUseCase,
    private val getUserProfileDetails: GetProfileDetailsUseCase,
    private val setSkillSelected: SetSkillSelectedUseCase,
    private val updateUserAsWorker: UpdateUserAsWorkerUseCase,
) : ViewModel() {

    val isRegisterCompleteDialogDisplayed = MutableLiveData(false)

    private val _openToWorkState = mutableStateOf(true)
    val openToWorkState: State<Boolean> = _openToWorkState

    private val _firstNameState = mutableStateOf(StandardTextFieldState())
    val firstNameState: State<StandardTextFieldState> = _firstNameState

    private val _lastNameState = mutableStateOf(StandardTextFieldState())
    val lastNameState: State<StandardTextFieldState> = _lastNameState

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _genderState = mutableStateOf(genderOptions[0])
    val genderState: State<String> = _genderState

    private val _ageState = mutableStateOf(StandardTextFieldState())
    val ageState: State<StandardTextFieldState> = _ageState

    private val _skillsState = mutableStateOf(SkillsState())
    val skillsState: State<SkillsState> = _skillsState

    private val _primaryCategory = mutableStateOf<WorkerCategory?>(null)
    val primaryCategory: State<WorkerCategory?> = _primaryCategory

    private val _profileImageUri = mutableStateOf<Uri?>(null)
    val profileImageUri: State<Uri?> = _profileImageUri

    private val _identityImageUri = mutableStateOf<Uri?>(null)
    val identityImageUri: State<Uri?> = _identityImageUri

    private val _updateWorkerState = mutableStateOf(UpdateWorkerState())
    val updateWorkerState: State<UpdateWorkerState> = _updateWorkerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<ProfileError>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _onUpdate = MutableSharedFlow<Unit>(replay = 1)
    val onUpdate = _onUpdate.asSharedFlow()

    init {
        viewModelScope.launch {
            val userId = getOwnUserId()
            getWorkerCategories()
            getWorkerProfileDetails(userId)
        }
    }

    fun onEvent(event: RegisterAsWorkerEvent) {
        when (event) {
            is RegisterAsWorkerEvent.ToggleOpenToWork -> {
                _openToWorkState.value = !openToWorkState.value
            }

            is RegisterAsWorkerEvent.CropProfilePicture -> {
                _profileImageUri.value = event.uri
            }

            is RegisterAsWorkerEvent.CropIdentityPicture -> {
                _identityImageUri.value = event.uri
            }

            is RegisterAsWorkerEvent.EnterAge -> {
                _ageState.value = ageState.value.copy(
                    text = event.age
                )
            }

            is RegisterAsWorkerEvent.EnterBio -> {
                _bioState.value = bioState.value.copy(
                    text = event.bio
                )
            }

            is RegisterAsWorkerEvent.EnterEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }

            is RegisterAsWorkerEvent.EnterFirstName -> {
                _firstNameState.value = firstNameState.value.copy(
                    text = event.firstName
                )
            }

            is RegisterAsWorkerEvent.GenderSelectedChanged -> {
                _genderState.value = event.gender
            }

            is RegisterAsWorkerEvent.EnterLastName -> {
                _lastNameState.value = lastNameState.value.copy(
                    text = event.lastName
                )
            }

            is RegisterAsWorkerEvent.SetSkillSelected -> {
                val result = setSkillSelected(
                    selectedSkills = skillsState.value.selectedSkills,
                    event.workerCategory
                )
                viewModelScope.launch {
                    when (result) {
                        is Resource.Success -> {
                            _skillsState.value = skillsState.value.copy(
                                selectedSkills = result.data ?: kotlin.run {
                                    _eventFlow.emit(UiEvent.MakeToast(UiText.unknownError()))
                                    return@launch
                                }
                            )
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.MakeToast(
                                    uiText = result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                    }
                }
            }

            is RegisterAsWorkerEvent.PrimarySkillSelected -> {
                _primaryCategory.value = event.workerCategory
            }

            is RegisterAsWorkerEvent.EnterSelectedSkillDailyWage -> {
                try {
                    val index = event.index
                    if (!containsMultipleDots(event.dailyWage)) {
                        val updatedSkills = skillsState.value.selectedSkills.toMutableList()
                        updatedSkills[index] =
                            updatedSkills[index].copy(dailyWage = event.dailyWage)
                        _skillsState.value = skillsState.value.copy(selectedSkills = updatedSkills)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            is RegisterAsWorkerEvent.EnterSelectedSkillHourlyWage -> {
                try {
                    val index = event.index
                    if (event.hourlyWage.contains('.') && event.hourlyWage.last() == '.') {
                        return
                    }
                    val updatedSkills = skillsState.value.selectedSkills.toMutableList()
                    updatedSkills[index] = updatedSkills[index].copy(hourlyWage = event.hourlyWage)
                    _skillsState.value = skillsState.value.copy(selectedSkills = updatedSkills)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            RegisterAsWorkerEvent.SetDefaultWages -> {
                setDefaultWages()
            }

            RegisterAsWorkerEvent.UpdateWorker -> {
                updateWorker()
            }
        }
    }

    private suspend fun getWorkerCategories() {
        when (val result = getCategories()) {
            is Resource.Success -> {
                _skillsState.value = _skillsState.value.copy(
                    skills = result.data?.map { it.toWorkerCategory() } ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.MakeToast(
                                UiText.StringResource(R.string.oops_couldn_t_load_worker_categories)
                            )
                        )
                        return
                    }
                )
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.MakeToast(
                        result.uiText ?: UiText.unknownError()
                    )
                )
            }
        }
    }

    private suspend fun getWorkerProfileDetails(id: String) {
        _updateWorkerState.value = updateWorkerState.value.copy(
            isLoading = true,
            loadingText = R.string.fetching_worker_data
        )
        when (val result = getUserProfileDetails(id)) {
            is Resource.Success -> {
                val profile = result.data ?: kotlin.run {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            UiText.StringResource(R.string.oops_couldn_t_load_profile)
                        )
                    )
                    return
                }
                _openToWorkState.value = profile.openToWork
                _firstNameState.value = firstNameState.value.copy(
                    text = profile.firstName
                )
                _lastNameState.value = lastNameState.value.copy(
                    text = profile.lastName
                )
                _emailState.value = emailState.value.copy(
                    text = profile.email
                )
                _bioState.value = bioState.value.copy(
                    text = profile.bio
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
                _skillsState.value = skillsState.value.copy(
                    selectedSkills = profile.categoryList?.map { workerCategory ->
                        val categoryId = workerCategory.id
                        val matchingSkill =
                            _skillsState.value.skills.firstOrNull { it.id == categoryId }
                        workerCategory.copy(
                            title = matchingSkill?.title,
                            skill = matchingSkill?.skill,
                            dailyMinWage = matchingSkill?.dailyMinWage,
                            hourlyMinWage = matchingSkill?.hourlyMinWage,
                        )
                    } ?: emptyList()
                )
                _primaryCategory.value = _skillsState.value.skills.find {
                    it.id == profile.primaryCategory
                }
                _updateWorkerState.value = updateWorkerState.value.copy(
                    isLoading = false,
                    profile = profile
                )
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                )
                _updateWorkerState.value = updateWorkerState.value.copy(
                    isLoading = false,
                )
            }
        }
    }

    private fun setWorkerCategoryAsPrimary(workerCategory: WorkerCategory) {
        _primaryCategory.value = workerCategory
    }

    private fun updateWorker() {
        _updateWorkerState.value = updateWorkerState.value.copy(
            isLoading = true,
            loadingText = R.string.updating_worker_data
        )
        if (_skillsState.value.selectedSkills.size == 1) {
            setWorkerCategoryAsPrimary(_skillsState.value.selectedSkills.first())
        }
        viewModelScope.launch {
            _firstNameState.value = firstNameState.value.copy(error = null)
            _emailState.value = emailState.value.copy(error = null)
            _bioState.value = bioState.value.copy(error = null)
            _ageState.value = ageState.value.copy(error = null)
            _skillsState.value = skillsState.value.copy(error = null)
            validateCategoryWages()

            val updateWorkerResult = updateUserAsWorker(
                UpdateWorkerData(
                    userId = getOwnUserId(),
                    openToWork = _openToWorkState.value,
                    firstName = _firstNameState.value.text,
                    lastName = _lastNameState.value.text,
                    email = _emailState.value.text,
                    bio = _bioState.value.text,
                    gender = _genderState.value,
                    age = if (_ageState.value.text.isBlank()) 0 else _ageState.value.text.toInt(),
                    categoryList = _skillsState.value.selectedSkills,
                    primarySkill = _primaryCategory.value
                ),
                profilePictureUri = profileImageUri.value
            )

            if (updateWorkerResult.firstNameError != null) {
                _errorFlow.emit(
                    updateWorkerResult.firstNameError
                )
            } else if (updateWorkerResult.emailError != null) {
                _errorFlow.emit(
                    updateWorkerResult.emailError
                )
            } else if (updateWorkerResult.bioError != null) {
                _errorFlow.emit(
                    updateWorkerResult.bioError
                )
            } else if (updateWorkerResult.ageError != null) {
                _errorFlow.emit(
                    updateWorkerResult.ageError
                )
            } else if (updateWorkerResult.skillsError != null) {
                _errorFlow.emit(
                    updateWorkerResult.skillsError
                )
            } else if (updateWorkerResult.skillsWageError != null) {
                _errorFlow.emit(
                    updateWorkerResult.skillsWageError
                )
            } else if (updateWorkerResult.primarySkillError != null) {
                _errorFlow.emit(
                    updateWorkerResult.primarySkillError
                )
            } else {
                when (updateWorkerResult.result) {
                    is Resource.Success -> {
                        _onUpdate.emit(Unit)
                        _updateWorkerState.value = updateWorkerState.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.MakeToast(
                                uiText = updateWorkerResult.result.uiText ?: UiText.unknownError()
                            )
                        )
                        _updateWorkerState.value = updateWorkerState.value.copy(
                            isLoading = false
                        )
                    }

                    null -> {
                        _updateWorkerState.value = updateWorkerState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun setDefaultWages() {
        val updatedSkills = skillsState.value.selectedSkills.toMutableList()
        for (index in updatedSkills.indices) {
            updatedSkills[index] =
                updatedSkills[index].copy(
                    dailyWage = updatedSkills[index].dailyWage,
                    hourlyWage = updatedSkills[index].hourlyWage
                )
            _skillsState.value = skillsState.value.copy(selectedSkills = updatedSkills)
        }
    }

    private fun validateCategoryWages() {
        val updatedSkills = skillsState.value.selectedSkills.toMutableList()
        for (index in updatedSkills.indices) {
            updatedSkills[index] =
                updatedSkills[index].copy(
                    dailyWage = ValidationUtil.validateWage(
                        updatedSkills[index].dailyWage,
                        updatedSkills[index].dailyMinWage ?: 0f
                    ).toString(),
                    hourlyWage = ValidationUtil.validateWage(
                        updatedSkills[index].hourlyWage,
                        updatedSkills[index].hourlyMinWage ?: 0f
                    ).toString(),
                )
            _skillsState.value = skillsState.value.copy(selectedSkills = updatedSkills)
        }
    }

    private fun containsMultipleDots(text: String): Boolean {
        val dotCount = text.count { it == '.' }
        return dotCount > 1
    }
}