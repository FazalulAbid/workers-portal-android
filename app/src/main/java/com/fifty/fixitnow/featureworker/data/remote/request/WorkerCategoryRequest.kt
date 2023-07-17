package com.fifty.fixitnow.featureworker.data.remote.request

data class WorkerCategoryRequest(
    val id: String,
    val dailyWage: Float,
    val hourlyWage: Float
)
