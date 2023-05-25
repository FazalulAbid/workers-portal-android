package com.fifty.workersportal.core.data.dto.response

import com.google.gson.annotations.SerializedName

data class BasicApiResponse<T>(
    @SerializedName("status")
    val successful: Boolean,
    val message: String? = null,
    @SerializedName("errors")
    val error: String? = null,
    val data: T? = null
)