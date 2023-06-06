package com.fifty.workersportal.featureworker.domain.model

import com.fifty.workersportal.featureworker.data.remote.dto.WorkerCategoryDto

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