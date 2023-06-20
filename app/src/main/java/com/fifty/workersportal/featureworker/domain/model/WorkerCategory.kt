package com.fifty.workersportal.featureworker.domain.model

import com.google.gson.annotations.SerializedName

data class WorkerCategory(
    @SerializedName("_id")
    val id: String,
    val title: String?,
    val skill: String?,
    val imageUrl: String?,
    val dailyMinWage: Float?,
    val hourlyMinWage: Float?,
    val dailyWage: String,
    val hourlyWage: String
)