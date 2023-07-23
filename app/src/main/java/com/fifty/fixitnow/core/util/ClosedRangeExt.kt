package com.fifty.fixitnow.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun ClosedRange<LocalDate>.formatDateRange(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMM ''yy")
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.getDefault())

    val startDateFormatted = start.format(dateFormatter)
    val endDateFormatted = endInclusive.format(dateFormatter)
    val startDayOfWeek = start.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val endDayOfWeek = endInclusive.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    return "$startDateFormatted, $startDayOfWeek to $endDateFormatted, $endDayOfWeek"
}