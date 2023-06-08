package com.fifty.workersportal.featureworker.data.remote.request

import com.google.gson.annotations.SerializedName

data class WorkerCategoryRequest(
    val id: String,
    val dailyWage: Float,
    val hourlyWage: Float
)
