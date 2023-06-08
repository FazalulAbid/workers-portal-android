package com.fifty.workersportal.featureworker.data.remote.dto

import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val skill: String,
    val imageUrl: String,
    val dailyMinWage: Float,
    val hourlyMinWage: Float,
    @SerializedName("__v")
    val versionKey: Int
) {
    fun toCategory(): Category =
        Category(
            id = id,
            title = title,
            skill = skill,
            imageUrl = imageUrl,
            dailyMinWage = dailyMinWage,
            hourlyMinWage = hourlyMinWage,
        )
}