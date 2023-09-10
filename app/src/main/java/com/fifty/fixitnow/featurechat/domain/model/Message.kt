package com.fifty.fixitnow.featurechat.domain.model

data class Message(
    val from: String,
    val to: String,
    val type: String,
    val content: String,
    val sentAt: String,
    val status: Boolean,
    val id: String
)
