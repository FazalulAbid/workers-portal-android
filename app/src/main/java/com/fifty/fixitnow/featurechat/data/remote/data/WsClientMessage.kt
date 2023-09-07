package com.fifty.fixitnow.featurechat.data.remote.data

data class WsClientMessage(
    val to: String,
    val type: String,
    val content: String
)
