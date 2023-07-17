package com.fifty.fixitnow.featureuser.data.remote

import com.google.gson.annotations.SerializedName

data class FavouriteUpdateRequest(
    @SerializedName("addedUserId")
    val userId: String
)
