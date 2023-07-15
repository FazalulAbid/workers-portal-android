package com.fifty.workersportal.featureworker.data.remote.dto

import com.fifty.workersportal.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.google.gson.annotations.SerializedName

data class WorkerDto(
    @SerializedName("_id")
    val workerId: String,
    val firstName: String,
    val lastName: String,
    val isVerified: Boolean,
    val gender: String,
    val bio: String,
    val categoryList: List<WorkerCategoryDto>,
    val openToWork: Boolean,
    val userId: String,
    val profileImageUrl: String? = null,
    val ratingAverage: Float? = null,
    val ratingCount: Int,
    @SerializedName("address")
    val localAddress: LocalAddressDto? = null,
    val isFavourite: Boolean,
    val primaryCategoryId: String,
    val distance: Double
) {
    fun toWorker(): Worker {
        return Worker(
            workerId = workerId,
            firstName = firstName,
            lastName = lastName,
            isVerified = isVerified,
            gender = gender,
            bio = bio,
            categoryList = categoryList.map { it.toWorkerCategory() },
            openToWork = openToWork,
            userId = userId,
            profileImageUrl = profileImageUrl,
            ratingAverage = ratingAverage,
            ratingCount = ratingCount,
            localAddress = localAddress?.toLocalAddress(),
            isFavourite = isFavourite,
            primaryCategoryId = primaryCategoryId,
            distance = distance
        )
    }
}
