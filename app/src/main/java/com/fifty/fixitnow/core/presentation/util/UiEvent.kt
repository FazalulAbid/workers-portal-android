package com.fifty.fixitnow.core.presentation.util

import com.fifty.fixitnow.core.util.Event
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal

sealed class UiEvent : Event() {
    data class ShowSnackBar(val uiText: UiText) : UiEvent()
    data class ShowMessage(val uiText: UiText) : UiEvent()
    data class MakeToast(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object SentWorkProposal : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()
    object OnLogout : UiEvent()
    object OnCurrentLocation : UiEvent()
    object WorkerProfileUpdated : UiEvent()
    object HideKeyboard : UiEvent()
    object ReviewAndRatingPosted : UiEvent()
    object OnBoardingScreenComplete : UiEvent()
}