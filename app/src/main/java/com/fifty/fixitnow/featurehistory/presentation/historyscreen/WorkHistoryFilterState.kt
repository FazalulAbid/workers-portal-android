package com.fifty.fixitnow.featurehistory.presentation.historyscreen

data class WorkHistoryFilterState(
    val pendingWorks: Boolean = true,
    val completedWorks: Boolean = true,
    val cancelledWorks: Boolean = true,
    val workHistory: Boolean = true,
    val hiringHistory: Boolean = true
)