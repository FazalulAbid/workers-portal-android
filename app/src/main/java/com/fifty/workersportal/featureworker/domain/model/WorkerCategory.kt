package com.fifty.workersportal.featureworker.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkerCategory(
    @SerializedName("_id")
    val id: String,
    val title: String?,
    val skill: String?,
    val imageUrl: String?,
    val dailyMinWage: Float?,
    val hourlyMinWage: Float?,
    val dailyWage: String,
    val hourlyWage: String
): Parcelable