package com.fifty.fixitnow.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun LocalDate.toDate(): Date {
    val instant = this.atStartOfDay(ZoneOffset.UTC).toInstant()
    return Date.from(instant)
}

fun LocalDate.formatToDateWithDay(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, EEEE", Locale.getDefault())
    return this.format(dateFormatter)
}

fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}

fun Long.toFormattedDateWithDay(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, EEEE")
    return instant.atZone(ZoneOffset.UTC).format(formatter)
}