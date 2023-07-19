package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import android.util.Range
import java.time.LocalDate

sealed class WorkHistoryEvent {
    data class ChangeSelectedRange(val range: Range<LocalDate>) : WorkHistoryEvent()
}
