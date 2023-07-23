package com.fifty.fixitnow.featurehistory.presentation.historyscreen

data class WorkHistoryFilterState(
    val selectedFilters: Set<WorkHistoryFilter> = enumValues<WorkHistoryFilter>().toSet(),
    val selectedHistoryCategories: Set<WorkHistoryCategory> = enumValues<WorkHistoryCategory>().toSet(),
//    val pendingWorks: Boolean = true,
//    val completedWorks: Boolean = true,
//    val cancelledWorks: Boolean = true,
//    val workHistory: Boolean = true,
//    val hiringHistory: Boolean = true
)

enum class WorkHistoryFilter(val label: String) {
    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

enum class WorkHistoryCategory(val label: String) {
    HIRING("Hiring History"),
    WORKING("Work History")
}