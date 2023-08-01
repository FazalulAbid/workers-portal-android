package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import androidx.compose.ui.graphics.Color

enum class WorkHistoryStatus(val status: String, val color: Color) {
    COMPLETED("Work is completed!", Color.Green),
    PENDING("Work is pending", Color.Blue),
    CANCELLED_BY_USER("Work cancelled by you", Color.Red),
    CANCELLED_BY_WORKER("Work cancelled by worker", Color.Red),
    REJECTED("Worker Rejected", Color.Red)
}