package com.fifty.workersportal.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.days


fun Long.toDaysAgo(): String {
    val startDate = LocalDate.now()
    val endDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    val period = Period.between(endDate, startDate)
    val days = period.days
    return when (period.days) {
        0 -> "Today"
        1 -> "Yesterday"
        else -> "$days days ago"
    }
}
