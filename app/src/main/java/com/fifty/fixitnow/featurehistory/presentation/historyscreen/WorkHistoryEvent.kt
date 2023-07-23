package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import android.util.Range
import java.time.LocalDate

sealed class WorkHistoryEvent {
    data class ChangeSelectedRange(val range: Range<LocalDate>) : WorkHistoryEvent()
    object ToggleWorkHistory : WorkHistoryEvent()
    object ToggleHiringHistory : WorkHistoryEvent()
    object TogglePendingFilter : WorkHistoryEvent()
    object ToggleCompletedFilter : WorkHistoryEvent()
    object ToggleCancelledFilter : WorkHistoryEvent()
    object OnSheetDismiss : WorkHistoryEvent()
    object OnApplyFilterClick : WorkHistoryEvent()
    object OnClearFilterClick : WorkHistoryEvent()
}
