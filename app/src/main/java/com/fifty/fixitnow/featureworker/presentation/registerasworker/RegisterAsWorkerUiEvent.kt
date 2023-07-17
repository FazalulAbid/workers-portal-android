package com.fifty.fixitnow.featureworker.presentation.registerasworker

import com.fifty.fixitnow.core.util.Event

sealed class RegisterAsWorkerUiEvent : Event() {
    data class SelectedCategoryCount(val count: Int) : RegisterAsWorkerUiEvent()
}
