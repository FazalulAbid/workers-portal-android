package com.fifty.fixitnow.featurechat.data.remote.data

data class WsServerMessage(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?
)