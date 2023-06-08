package com.fifty.workersportal.featureworker.domain.model

data class WorkerCategory(
    val id: String,
    val title: String?,
    val skill: String?,
    val imageUrl: String?,
    val dailyMinWage: Float?,
    val hourlyMinWage: Float?,
    val dailyWage: String,
    val hourlyWage: String
)