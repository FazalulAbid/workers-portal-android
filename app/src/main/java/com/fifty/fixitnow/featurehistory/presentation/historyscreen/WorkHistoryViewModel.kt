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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkHistoryViewModel @Inject constructor(

) : ViewModel() {

    private val _selectedRange = mutableStateOf<ClosedRange<LocalDate>>(
        LocalDate.now().minusYears(2).let { time -> time.plusDays(5)..time.plusDays(8) }
    )
    val selectedRange: State<ClosedRange<LocalDate>> = _selectedRange

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: WorkHistoryEvent) {
        when (event) {
            is WorkHistoryEvent.ChangeSelectedRange -> {
                _selectedRange.value = event.range.toClosedRange()
            }
        }
    }

}