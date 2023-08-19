package com.fifty.fixitnow.featureauth.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.featureauth.domain.usecase.ReadOnBoardingStateUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveOnBoardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveOnBoardingStateUseCase: SaveOnBoardingStateUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.OnBoardingScreenComplete -> {
                viewModelScope.launch {
                    saveOnBoardingState(event.value)
                    _eventFlow.emit(UiEvent.OnBoardingScreenComplete)
                }
            }
        }
    }

    private suspend fun saveOnBoardingState(completed: Boolean) {
        saveOnBoardingStateUseCase(completed = completed)
    }
}