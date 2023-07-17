package com.fifty.fixitnow.core.presentation.component

import android.util.Range
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.util.toRange
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MultipleDateChooser(closeSelection: UseCaseState.() -> Unit) {

    val timeBoundary = LocalDate.now().let { now -> now.minusYears(2)..now }
    val selectedRange = remember {
        val default =
            LocalDate.now().minusYears(2).let { time -> time.plusDays(5)..time.plusDays(8) }
        mutableStateOf(default.toRange())
    }

    CalendarDialog(
        state = rememberUseCaseState(visible = true, true, onCloseRequest = closeSelection),
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            boundary = timeBoundary,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period(
            selectedRange = selectedRange.value
        ) { startDate, endDate ->
            selectedRange.value = Range(startDate, endDate)
        },
    )
}