package com.fifty.workersportal.featureworker.data.remote.dto

import com.fifty.workersportal.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.google.gson.annotations.SerializedName

data class WorkerDto(
    @SerializedName("_id")
    val workerId: String,
    val firstName: String,
    val lastName: String,
    val isFavourite: Boolean,
    val userId: String,
    val profileImageUrl: String? = null,
    val ratingAverage: Float? = null,
    val ratingCount: Int,
    val primaryCategoryName: String,
    val primaryCategoryDailyWage: Float,
    @SerializedName("address")
    val localAddress: LocalAddressDto? = null
) {
    fun toWorker(): Worker {
        return Worker(
            workerId = workerId,
            firstName = firstName,
            lastName = lastName,
            isFavourite = isFavourite,
            profileImageUrl = profileImageUrl,
            ratingAverage = ratingAverage,
            ratingCount = ratingCount,
            primaryCategoryName = primaryCategoryName,
            primaryCategoryDailyWage = primaryCategoryDailyWage,
            localAddress = localAddress?.toLocalAddress()
        )
    }
}
