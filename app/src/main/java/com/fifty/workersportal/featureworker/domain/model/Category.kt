package com.fifty.workersportal.featureworker.domain.model

data class Category(
    val id: String,
    val title: String,
    val skill: String,
    val imageUrl: String?,
    val dailyMinWage: Float,
    val hourlyMinWage: Float,
) {

    fun toWorkerCategory(): WorkerCategory =
        WorkerCategory(
            id = id,
            title = title,
            skill = skill,
            imageUrl = imageUrl,
            dailyMinWage = dailyMinWage,
            hourlyMinWage = hourlyMinWage,
            dailyWage = dailyMinWage.toString(),
            hourlyWage = hourlyMinWage.toString()
        )
}
