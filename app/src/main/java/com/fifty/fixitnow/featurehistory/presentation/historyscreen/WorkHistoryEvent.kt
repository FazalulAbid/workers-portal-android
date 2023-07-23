package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import android.util.Range
import java.time.LocalDate

sealed class WorkHistoryEvent {
    data class ChangeSelectedRange(val range: Range<LocalDate>) : WorkHistoryEvent()
    data class ToggleWorkHistoryCategory(val value: WorkHistoryCategory) : WorkHistoryEvent()
    data class ToggleFilter(val value: WorkHistoryFilter) : WorkHistoryEvent()
    object OnSheetDismiss : WorkHistoryEvent()
    object OnApplyFilterClick : WorkHistoryEvent()
    object OnClearFilterClick : WorkHistoryEvent()
}
