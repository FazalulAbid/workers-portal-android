package com.fifty.workersportal.core.presentation.util

import com.fifty.workersportal.core.util.Event
import com.fifty.workersportal.core.util.UiText

sealed class UiEvent : Event() {
    data class ShowSnackBar(val uiText: UiText) : UiEvent()
    data class ShowMessage(val uiText: UiText) : UiEvent()
    data class MakeToast(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()
    object OnLogout : UiEvent()
    object OnCurrentLocation : UiEvent()
    object WorkerProfileUpdated : UiEvent()
    object HideKeyboard : UiEvent()
    object ReviewAndRatingPosted : UiEvent()
}