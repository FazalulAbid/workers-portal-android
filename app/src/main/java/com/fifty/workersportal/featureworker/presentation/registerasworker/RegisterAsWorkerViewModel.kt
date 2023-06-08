package com.fifty.workersportal.featureworker.presentation.registerasworker

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants.genderOptions
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.fifty.workersportal.featureworker.domain.usecase.WorkerUseCases
import com.fifty.workersportal.featureworker.util.WorkerError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterAsWorkerViewModel @Inject constructor(
    private val workerUseCases: WorkerUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
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

    private val _primarySkill = mutableStateOf<WorkerCategory?>(null)
    val primarySkill: State<WorkerCategory?> = _primarySkill

    private val _updateWorkerState = mutableStateOf(UpdateWorkerState())
    val updateWorkerState: State<UpdateWorkerState> = _updateWorkerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<WorkerError>()
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

    private suspend fun getWorkerCategories() {
        when (val result = workerUseCases.getCategories()) {
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
                Log.d(
                    "Hello",
                    "getWorkerCategories:  Alllll Cates ${_skillsState.value.skills}"
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
        _eventFlow.emit(
            UiEvent.MakeToast(
                UiText.DynamicString(id)
            )
        )
        when (val result = workerUseCases.getUserProfileDetails(id)) {
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
                _genderState.value = profile.gender
                _ageState.value = ageState.value.copy(
                    text = profile.age.toString()
                )
                _skillsState.value = skillsState.value.copy(
                    selectedSkills = profile.categoryList?.map { workerCategory ->
                        workerCategory.copy(
                            title = _skillsState.value.skills.findLast { it.id == id }?.title,
                            skill = _skillsState.value.skills.findLast { it.id == id }?.skill,
                            dailyMinWage = _skillsState.value.skills.findLast { it.id == id }?.dailyMinWage,
                            hourlyMinWage = _skillsState.value.skills.findLast { it.id == id }?.hourlyMinWage,
                        )
                    } ?: emptyList()
                )
                _skillsState.value = skillsState.value.copy(
                    selectedSkills = profile.categoryList?.map { workerCategory ->
                        workerCategory.copy(
                            title = _skillsState.value.skills.findLast { it.id == id }?.title,
                            skill = _skillsState.value.skills.findLast { it.id == id }?.skill,
                            dailyMinWage = _skillsState.value.skills.findLast { it.id == id }?.dailyMinWage,
                            hourlyMinWage = _skillsState.value.skills.findLast { it.id == id }?.hourlyMinWage,
                        )
                    } ?: emptyList()
                )
                Log.d("Hello", "All Categories : ${_skillsState.value.skills}")
                Log.d("Hello", "selected Categories : ${_skillsState.value.selectedSkills}")
                Log.d("Hello", "Primary category : ${profile.primaryCategory}")
                _primarySkill.value = profile.primaryCategory?.copy(
                    title = _skillsState.value.selectedSkills.find { it.id == id }?.title,
                    skill = _skillsState.value.selectedSkills.find { it.id == id }?.skill,
                    dailyMinWage = _skillsState.value.selectedSkills.find { it.id == id }?.dailyMinWage,
                    hourlyMinWage = _skillsState.value.selectedSkills.find { it.id == id }?.hourlyMinWage,
                )
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                )
            }
        }
    }

    fun onEvent(event: RegisterAsWorkerEvent) {
        when (event) {
            is RegisterAsWorkerEvent.ToggleOpenToWork -> {
                _openToWorkState.value = !openToWorkState.value
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
                val result = workerUseCases.setSkillSelected(
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
                _primarySkill.value = event.workerCategory
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

    private fun setWorkerCategoryAsPrimary(workerCategory: WorkerCategory) {
        _primarySkill.value = workerCategory
    }

    private fun updateWorker() {
        if (_skillsState.value.selectedSkills.size == 1) {
            setWorkerCategoryAsPrimary(_skillsState.value.selectedSkills.first())
        }
        viewModelScope.launch {
            _firstNameState.value = firstNameState.value.copy(error = null)
            _emailState.value = emailState.value.copy(error = null)
            _bioState.value = bioState.value.copy(error = null)
            _ageState.value = ageState.value.copy(error = null)
            _skillsState.value = skillsState.value.copy(error = null)
            _updateWorkerState.value = UpdateWorkerState(isLoading = true)
            validateCategoryWages()

            val updateWorkerResult = workerUseCases.updateUserAsWorker(
                UpdateWorkerData(
                    openToWork = _openToWorkState.value,
                    firstName = _firstNameState.value.text,
                    lastName = _lastNameState.value.text,
                    email = _emailState.value.text,
                    bio = _bioState.value.text,
                    gender = _genderState.value,
                    age = if (_ageState.value.text.isBlank()) 0 else _ageState.value.text.toInt(),
                    categoryList = _skillsState.value.selectedSkills,
                    primarySkill = _primarySkill.value
                )
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
            }

            when (updateWorkerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            UiText.StringResource(R.string.you_are_registered_as_worker)
                        )
                    )
                    _onUpdate.emit(Unit)
                    _updateWorkerState.value = UpdateWorkerState(isLoading = false)
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = updateWorkerResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                    _updateWorkerState.value = UpdateWorkerState(isLoading = false)
                }

                null -> {
                    _updateWorkerState.value = UpdateWorkerState(isLoading = false)
                }
            }
        }
    }

    private fun setDefaultWages() {
        val updatedSkills = skillsState.value.selectedSkills.toMutableList()
        for (index in updatedSkills.indices) {
            updatedSkills[index] =
                updatedSkills[index].copy(
                    dailyWage = updatedSkills[index].hourlyMinWage.toString(),
                    hourlyWage = updatedSkills[index].dailyMinWage.toString()
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