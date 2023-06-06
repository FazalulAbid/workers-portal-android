package com.fifty.workersportal.featureworker.data.remote.request

import com.fifty.workersportal.featureworker.data.remote.dto.WorkerCategoryDto

data class UpdateWorkerRequest(
    val openToWork: Boolean,
    val firstName: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val gender: String,
    val age: Int,
    val categoryList: List<WorkerCategoryDto>
)
