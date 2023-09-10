package com.fifty.fixitnow.featurechat.data.remote.data

import com.fifty.fixitnow.featurechat.domain.model.Message

data class WsServerMessage(
    val from: String,
    val to: String,
    val type: String,
    val content: String,
    val sentAt: Any?,
    val status: Boolean,
    val _id: String,
    val __v: Int
) {
    fun toMessage(): Message {
        return Message(
            from = from,
            to = to,
            type = type,
            content = content,
            sentAt = "",
            status = status,
            id = _id
        )
    }
}