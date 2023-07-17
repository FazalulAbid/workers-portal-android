package com.fifty.fixitnow.featureworker.data.remote.dto

import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.google.gson.annotations.SerializedName

data class WorkerCategoryDto(
    @SerializedName("id")
    val id: String,
    val title: String,
    val skill: String,
    val hourlyWage: Float,
    val dailyWage: Float,
    val imageUrl: String,
    @SerializedName("__v")
    val versionKey: Int
) {
    fun toWorkerCategory(): WorkerCategory {
        return WorkerCategory(
            id = id,
            title = title,
            skill = skill,
            imageUrl = imageUrl,
            dailyMinWage = null,
            hourlyMinWage = null,
            dailyWage = dailyWage.toString(),
            hourlyWage = hourlyWage.toString()
        )
    }
}
