package com.fifty.fixitnow.featureworker.data.remote.dto

import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class SampleWorkDto(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val timestamp: Long,
    val userId: String,
    @SerializedName("__v")
    val versionKey: Int
) {
    fun toSampleWork(): SampleWork =
        SampleWork(
            id = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
            formattedDateTime = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
                .format(timestamp),
        )
}