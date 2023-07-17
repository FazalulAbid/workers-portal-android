package com.fifty.fixitnow.featureworker.data.remote.dto

import com.fifty.fixitnow.featurelocation.data.remote.dto.LocalAddressDto
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.RoundingMode

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
            ratingAverage = BigDecimal((ratingAverage ?: 0f).toDouble()).setScale(
                1, RoundingMode.HALF_UP
            ).toFloat(),
            ratingCount = ratingCount,
            localAddress = localAddress?.toLocalAddress(),
            isFavourite = isFavourite,
            primaryCategoryId = primaryCategoryId,
            distance = distance
        )
    }
}
