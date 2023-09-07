package com.fifty.fixitnow.featurechat.data.repository

import android.util.Log
import com.fifty.fixitnow.featurechat.data.remote.ChatApi
import com.fifty.fixitnow.featurechat.data.remote.ChatService
import com.fifty.fixitnow.featurechat.data.remote.data.WsClientMessage
import com.fifty.fixitnow.featurechat.data.remote.data.WsServerMessage
import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository
import com.google.gson.Gson
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatRepositoryImpl(
    private val chatApi: ChatApi,
    private val chatService: ChatService,
    private val gson: Gson
) : ChatRepository {

    override fun establishChatSocketConnection(url: String) {
        chatService.setSocket(url)
        chatService.establishConnection()
    }

    override fun closeSocketConnection() {
        chatService.closeConnection()
    }

    override fun sendMessage(toUserId: String, content: String) {
        val eventData = WsClientMessage(
            to = toUserId,
            type = "text",
            content = content
        )
        chatService.emit("message", gson.toJson(eventData))
    }

    override fun observeChatMessages(): Flow<WsServerMessage> =
        callbackFlow {
            val eventListener = Emitter.Listener { args ->
                Log.d("Hello", "invoke: ${args.size}, ${args[0]}")
                val message = gson.fromJson(args[0].toString(), WsServerMessage::class.java)
                trySend(message).isSuccess
            }

            chatService.on("message", eventListener)

            awaitClose {
                chatService.closeConnection()
            }
        }
}