package com.fifty.workersportal.featureworker.data.remote.request

data class GetWorkerCategoriesRequest(
    val page: Int,
    val pageSize: Int,
    val query: String,
    val categoryId: String?
)
