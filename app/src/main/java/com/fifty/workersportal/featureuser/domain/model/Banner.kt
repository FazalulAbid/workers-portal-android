package com.fifty.workersportal.featureuser.domain.model

data class Banner(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val deeplinkUrl: String
)
