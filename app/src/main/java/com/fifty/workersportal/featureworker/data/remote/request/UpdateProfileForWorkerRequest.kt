package com.fifty.workersportal.featureworker.data.remote.request

data class UpdateProfileForWorkerRequest(
    val openToWork: Boolean,
    val firstName: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val gender: String,
    val age: Int,
    val categoryList: List<WorkerCategoryRequest>
)
