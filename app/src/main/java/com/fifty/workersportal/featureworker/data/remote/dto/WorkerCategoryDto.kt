package com.fifty.workersportal.featureworker.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WorkerCategoryDto(
    @SerializedName("_id")
    val id: String,
    val hourlyWage: Float,
    val dailyWage: Float
)
