package com.fifty.fixitnow.core.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun LocalDate.toDate(): Date {
    val zoneId = ZoneId.systemDefault()
    val instant = this.atStartOfDay(zoneId).toInstant()
    return Date.from(instant)
}

fun LocalDate.toMillis(): Long {
    val localDateTime = this.atStartOfDay()
    val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}

fun Long.toFormattedDateWithDay(): String {
    val instant = Instant.ofEpochMilli(this)
    val zoneId = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, EEEE")
    return instant.atZone(zoneId).format(formatter)
}