package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.util.toClosedRange
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.featurehistory.domain.usecase.GetWorkHistoryRepostUseCase
import com.fifty.fixitnow.featureworker.presentation.searchworker.SearchWorkerFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WorkHistoryViewModel @Inject constructor(
    private val getWorkHistoryRepostUseCase: GetWorkHistoryRepostUseCase
) : ViewModel() {

    private val _filterState = mutableStateOf(WorkHistoryFilterState())

    private val _tempFilterState = mutableStateOf(WorkHistoryFilterState())
    val tempFilterState: State<WorkHistoryFilterState> = _tempFilterState

    private val _selectedRange = mutableStateOf<ClosedRange<LocalDate>>(
        LocalDate.now().minusDays(30)..LocalDate.now()
    )
    val selectedRange: State<ClosedRange<LocalDate>> = _selectedRange

    private val _tempSelectedRange = mutableStateOf<ClosedRange<LocalDate>>(
        LocalDate.now().minusDays(30)..LocalDate.now()
    )
    val tempSelectedRange: State<ClosedRange<LocalDate>> = _tempSelectedRange

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: WorkHistoryEvent) {
        when (event) {
            is WorkHistoryEvent.ChangeSelectedRange -> {
                _tempSelectedRange.value = event.range.toClosedRange()
            }

            WorkHistoryEvent.OnSheetDismiss -> {
                _tempFilterState.value = _filterState.value
                _tempSelectedRange.value = selectedRange.value
            }

            is WorkHistoryEvent.ToggleFilter -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    selectedFilters =
                    _tempFilterState.value.selectedFilters.toggleSelection(event.value)
                )
            }

            is WorkHistoryEvent.ToggleWorkHistoryCategory -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    selectedHistoryCategories =
                    _tempFilterState.value.selectedHistoryCategories.toggleSelection(event.value)
                )
            }

            WorkHistoryEvent.OnApplyFilterClick -> {
                _selectedRange.value = _tempSelectedRange.value
                _filterState.value = _tempFilterState.value

                //testing
                viewModelScope.launch {
                    getWorkHistoryRepostUseCase()
                }
            }

            WorkHistoryEvent.OnClearFilterClick -> {
                resetFiltersToDefault()
            }
        }
    }

    private fun resetFiltersToDefault() {
        _filterState.value = WorkHistoryFilterState()
        _tempFilterState.value = _filterState.value
        _selectedRange.value = LocalDate.now().minusDays(30)..LocalDate.now()
        _tempSelectedRange.value = _selectedRange.value
    }
}

fun <T> Set<T>.toggleSelection(item: T): Set<T> {
    return if (this.contains(item)) {
        this - item
    } else {
        this + item
    }
}
