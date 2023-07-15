package com.fifty.workersportal.featureworker.domain.model

import android.os.Parcelable
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Worker(
    @SerializedName("_id")
    val workerId: String,
    val firstName: String,
    val lastName: String,
    val isVerified: Boolean,
    val gender: String,
    val bio: String,
    val categoryList: List<WorkerCategory>,
    val openToWork: Boolean,
    val userId: String,
    val profileImageUrl: String? = null,
    val ratingAverage: Float? = null,
    val ratingCount: Int,
    @SerializedName("address")
    val localAddress: LocalAddress? = null,
    val isFavourite: Boolean,
    val primaryCategoryId: String,
    val distance: Double
) : Parcelable