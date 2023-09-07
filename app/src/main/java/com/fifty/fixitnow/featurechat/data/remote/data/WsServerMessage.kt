package com.fifty.fixitnow.featurechat.data.remote.data

data class WsServerMessage(
    val from: String,
    val to: String,
    val type: String,
    val content: String,
    val sentAt: String,
    val status: Boolean,
    val _id: String,
    val __v: Int
)