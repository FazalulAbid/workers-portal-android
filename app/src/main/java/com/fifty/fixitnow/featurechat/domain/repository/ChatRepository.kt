package com.fifty.fixitnow.featurechat.domain.repository

import com.fifty.fixitnow.featurechat.data.remote.data.WsServerMessage
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.Flow


interface ChatRepository {

    fun establishChatSocketConnection(url: String)

    fun closeSocketConnection()

    fun sendMessage(toUserId: String, content: String)

    fun observeChatMessages(): Flow<WsServerMessage>
}