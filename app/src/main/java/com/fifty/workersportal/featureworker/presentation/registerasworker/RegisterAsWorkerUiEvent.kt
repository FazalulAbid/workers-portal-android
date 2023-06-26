package com.fifty.workersportal.featureworker.presentation.registerasworker

import com.fifty.workersportal.core.util.Event

sealed class RegisterAsWorkerUiEvent : Event() {
    data class SelectedCategoryCount(val count: Int) : RegisterAsWorkerUiEvent()
}
