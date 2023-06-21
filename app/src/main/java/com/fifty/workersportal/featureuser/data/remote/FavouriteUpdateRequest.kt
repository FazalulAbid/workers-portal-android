package com.fifty.workersportal.featureuser.data.remote

import com.google.gson.annotations.SerializedName

data class FavouriteUpdateRequest(
    @SerializedName("addedUserId")
    val userId: String
)
