package com.fifty.workersportal.core.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val phone: String,
    val countryCode: String,
    val firstName: String,
    val isWorker: Boolean,
    val lastName: String,
)
