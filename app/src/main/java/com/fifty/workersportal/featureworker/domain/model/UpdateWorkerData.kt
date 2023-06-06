package com.fifty.workersportal.featureworker.domain.model

data class UpdateWorkerData(
    val openToWork: Boolean,
    val firstName: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val gender: String,
    val age: Int,
    val categoryList: List<WorkerCategory>
)