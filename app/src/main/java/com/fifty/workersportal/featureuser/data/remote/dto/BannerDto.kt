package com.fifty.workersportal.featureuser.data.remote.dto

import com.fifty.workersportal.featureuser.domain.model.Banner
import com.google.gson.annotations.SerializedName

data class BannerDto(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val deeplinkUrl: String,
    @SerializedName("__v")
    val versionKey: Int
) {

    fun toBanner(): Banner =
        Banner(
            title = title,
            description = description,
            imageUrl = imageUrl,
            deeplinkUrl = deeplinkUrl
        )
}
