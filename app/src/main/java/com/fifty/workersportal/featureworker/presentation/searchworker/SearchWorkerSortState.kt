package com.fifty.workersportal.featureworker.presentation.searchworker

data class SearchWorkerSortState(
    val isRatingHighToLow: Boolean = false,
    val isDistanceLowToHigh: Boolean = false,
    val isWageLowToHigh: Boolean = false,
    val isWageHighToLow: Boolean = false
)
