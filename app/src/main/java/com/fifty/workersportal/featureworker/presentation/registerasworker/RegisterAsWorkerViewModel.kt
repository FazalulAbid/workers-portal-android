package com.fifty.workersportal.featureworker.presentation.registerasworker

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants.genderOptions
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.usecase.RegisterAsWorkerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterAsWorkerViewModel @Inject constructor(
    private val registerAsWorkerUseCases: RegisterAsWorkerUseCases
) : ViewModel() {

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

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                val result = registerAsWorkerUseCases.setSkillSelected(
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

            RegisterAsWorkerEvent.RegisterAsWorker -> {

            }
        }
    }

    private fun setDefaultWages() {
        val updatedSkills = skillsState.value.selectedSkills.toMutableList()
        for (index in updatedSkills.indices) {
            updatedSkills[index] =
                updatedSkills[index].copy(
                    dailyWage = updatedSkills[index].minHourlyWage.toString(),
                    hourlyWage = updatedSkills[index].minDailyWage.toString()
                )
            _skillsState.value = skillsState.value.copy(selectedSkills = updatedSkills)
        }
    }

    private fun containsMultipleDots(text: String): Boolean {
        val dotCount = text.count { it == '.' }
        return dotCount > 1
    }
}