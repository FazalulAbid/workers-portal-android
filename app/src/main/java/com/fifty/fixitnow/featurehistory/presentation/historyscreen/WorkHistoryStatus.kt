package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import androidx.compose.ui.graphics.Color

enum class WorkHistoryStatus(val status: String, val color: Color) {
    COMPLETED("Work is completed!", Color(0xFF11999E)),
    PENDING("Work is pending", Color(0xFF0F4C75)),
    CANCELLED_BY_USER("Work cancelled by you", Color(0xFFE23E57)),
    CANCELLED_BY_WORKER("Work cancelled by worker", Color(0xFFE23E57)),
    REJECTED("Worker Rejected", Color(0xFFE23E57))
}