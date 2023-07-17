package com.fifty.fixitnow.featureworker.presentation.registerasworker

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.fixitnow.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.fixitnow.core.domain.util.ValidationUtil
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Constants.genderOptions
import com.fifty.fixitnow.core.util.Event
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.model.UpdateWorkerData
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.fifty.fixitnow.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.SetSkillSelectedUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.UpdateUserAsWorkerUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.ValidateUserAsWorkerUseCase
import com.fifty.fixitnow.featureworker.util.ProfileError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterAsWorkerViewModel @Inject constructor(
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProfileDetailsUseCase: GetProfileDetailsUseCase,
    private val setSkillSelectedUseCase: SetSkillSelectedUseCase,
    private val validateUserAsWorkerUseCase: ValidateUserAsWorkerUseCase,
    private val updateUserAsWorkerUseCase: UpdateUserAsWorkerUseCase,
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

    private val _primaryCategoryId = mutableStateOf<String?>(null)
    val primaryCategoryId: State<String?> = _primaryCategoryId

    private val _profileImageUri = mutableStateOf<Uri?>(null)
    val profileImageUri: State<Uri?> = _profileImageUri

    private val _identityImageUri = mutableStateOf<Uri?>(null)
    val identityImageUri: State<Uri?> = _identityImageUri

    private val _updateWorkerState = mutableStateOf(UpdateWorkerState())
    val updateWorkerState: State<UpdateWorkerState> = _updateWorkerState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<ProfileError>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _onUpdate = MutableSharedFlow<Unit>(replay = 1)
    val onUpdate = _onUpdate.asSharedFlow()

    init {
        viewModelScope.launch {
            val userId = getOwnUserIdUseCase()
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
                val result = setSkillSelectedUseCase(
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
                _primaryCategoryId.value = event.workerCategory.id
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

            RegisterAsWorkerEvent.OnSaveCheckClick -> {
                viewModelScope.launch {
                    _updateWorkerState.value = updateWorkerState.value.copy(
                        isLoading = true,
                        loadingText = R.string.updating_worker_data
                    )
                    val validatedWorkerData = validateWorkerData()
                    validatedWorkerData?.let {
                        if (skillsState.value.selectedSkills.size == 1) {
                            updateUserAsWorker(it)
                        } else {
                            _errorFlow.emit(ProfileError.NoPrimarySkillSelected)
                        }
                    }
                    _eventFlow.emit(
                        RegisterAsWorkerUiEvent.SelectedCategoryCount(
                            skillsState.value.selectedSkills.size
                        )
                    )
                    _updateWorkerState.value = updateWorkerState.value.copy(
                        isLoading = false,
                    )
                }
            }

            RegisterAsWorkerEvent.UpdateWorker -> {
                viewModelScope.launch {
                    _updateWorkerState.value = updateWorkerState.value.copy(
                        isLoading = true,
                        loadingText = R.string.updating_worker_data
                    )
                    val updateWorkerData = validateWorkerData()
                    updateWorkerData?.let {
                        updateUserAsWorker(it)
                    }
                    _updateWorkerState.value = updateWorkerState.value.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    private suspend fun getWorkerCategories() {
        when (val result = getCategoriesUseCase()) {
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
        when (val result = getProfileDetailsUseCase(id)) {
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
                _primaryCategoryId.value = _skillsState.value.skills.find {
                    it.id == profile.primaryCategory
                }?.id
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
        _primaryCategoryId.value = workerCategory.id
    }

    private suspend fun validateWorkerData(): UpdateWorkerData? {
        if (_skillsState.value.selectedSkills.size == 1) {
            setWorkerCategoryAsPrimary(_skillsState.value.selectedSkills.first())
        }
        _firstNameState.value = firstNameState.value.copy(error = null)
        _emailState.value = emailState.value.copy(error = null)
        _bioState.value = bioState.value.copy(error = null)
        _ageState.value = ageState.value.copy(error = null)
        _skillsState.value = skillsState.value.copy(error = null)
        validateCategoryWages()

        val updateUserAsWorkerValidationResult = validateUserAsWorkerUseCase(
            UpdateWorkerData(
                userId = getOwnUserIdUseCase(),
                openToWork = _openToWorkState.value,
                firstName = _firstNameState.value.text,
                lastName = _lastNameState.value.text,
                email = _emailState.value.text,
                bio = _bioState.value.text,
                gender = _genderState.value,
                age = if (_ageState.value.text.isBlank()) 0 else _ageState.value.text.toInt(),
                categoryList = _skillsState.value.selectedSkills,
                primarySkillId = _primaryCategoryId.value
            ),
        )

        if (updateUserAsWorkerValidationResult.firstNameError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.firstNameError
            )
        } else if (updateUserAsWorkerValidationResult.emailError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.emailError
            )
        } else if (updateUserAsWorkerValidationResult.bioError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.bioError
            )
        } else if (updateUserAsWorkerValidationResult.ageError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.ageError
            )
        } else if (updateUserAsWorkerValidationResult.skillsError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.skillsError
            )
        } else if (updateUserAsWorkerValidationResult.skillsWageError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.skillsWageError
            )
        } else if (updateUserAsWorkerValidationResult.primarySkillError != null) {
            _errorFlow.emit(
                updateUserAsWorkerValidationResult.primarySkillError
            )
        } else {
            return updateUserAsWorkerValidationResult.result
        }
        return null
    }

    private suspend fun updateUserAsWorker(updateWorkerData: UpdateWorkerData) {
        val result = updateUserAsWorkerUseCase(
            updateWorkerData = updateWorkerData,
            profilePictureUri = profileImageUri.value,
            identityPictureUri = identityImageUri.value
        )
        when (result) {
            is Resource.Success -> {
                _onUpdate.emit(Unit)
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