package com.fifty.workersportal.core.util

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun LocalDate.toDate(): Date {
    val zoneId = ZoneId.systemDefault()
    val instant = this.atStartOfDay(zoneId).toInstant()
    return Date.from(instant)
}
