package com.fifty.fixitnow.featurechat.data.remote.data

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String
)
