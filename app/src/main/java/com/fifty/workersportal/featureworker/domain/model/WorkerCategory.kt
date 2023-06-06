package com.fifty.workersportal.featureworker.domain.model

data class WorkerCategory(
    val id: String,
    val name: String,
    val skill: String,
    val imageUrl: String,
    val minDailyWage: Float,
    val minHourlyWage: Float,
    val dailyWage: String,
    val hourlyWage: String
)