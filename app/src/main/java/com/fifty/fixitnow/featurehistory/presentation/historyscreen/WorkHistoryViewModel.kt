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
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.featureworker.presentation.searchworker.SearchWorkerFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WorkHistoryViewModel @Inject constructor(

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

            WorkHistoryEvent.ToggleCancelledFilter -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    cancelledWorks = !tempFilterState.value.cancelledWorks
                )
            }

            WorkHistoryEvent.ToggleCompletedFilter -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    completedWorks = !tempFilterState.value.completedWorks
                )
            }

            WorkHistoryEvent.TogglePendingFilter -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    pendingWorks = !tempFilterState.value.pendingWorks
                )
            }

            WorkHistoryEvent.ToggleWorkHistory -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    workHistory = !tempFilterState.value.workHistory
                )
            }

            WorkHistoryEvent.ToggleHiringHistory -> {
                _tempFilterState.value = tempFilterState.value.copy(
                    hiringHistory = !tempFilterState.value.hiringHistory
                )
            }

            WorkHistoryEvent.OnApplyFilterClick -> {
                _selectedRange.value = _tempSelectedRange.value
                _filterState.value = _tempFilterState.value
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