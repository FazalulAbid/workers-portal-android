package com.fifty.workersportal.core.util

import java.util.Calendar
import java.util.Date

fun Long.toDaysAgo(): String {
    val currentDate = Calendar.getInstance().time
    val timestampDate = Date(this)
    val diff = currentDate.time - timestampDate.time
    val daysAgo = diff / (24 * 60 * 60 * 1000)

    return when {
        daysAgo == 1L -> "$daysAgo day ago"
        daysAgo > 1L -> "$daysAgo days ago"
        else -> "Today"
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun Long.toDaysAgo(): String {
//    val now = LocalDate.now()
//    val timestamp = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
//    val daysAgo = now.toEpochDay() - timestamp.toEpochDay()
//
//    return when {
//        daysAgo == 1L -> "$daysAgo day ago"
//        daysAgo > 1L -> "$daysAgo days ago"
//        else -> "Today"
//    }
//}
