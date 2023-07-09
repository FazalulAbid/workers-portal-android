package com.fifty.workersportal.featureworker.domain.model

import com.fifty.workersportal.featurelocation.domain.model.LocalAddress

data class Worker(
    val workerId: String,
    val firstName: String,
    val lastName: String,
    val isFavourite: Boolean,
    val profileImageUrl: String?,
    val ratingAverage: Float?,
    val ratingCount: Int,
    val primaryCategoryName: String,
    val primaryCategoryDailyWage: Float,
    val localAddress: LocalAddress?
)
