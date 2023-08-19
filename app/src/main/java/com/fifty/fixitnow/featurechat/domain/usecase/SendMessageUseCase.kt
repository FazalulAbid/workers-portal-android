package com.fifty.fixitnow.featurechat.domain.usecase

import android.util.Log
import com.fifty.fixitnow.featurechat.data.remote.SocketManager
import org.json.JSONObject

class SendMessageUseCase(
    private val socketManager: SocketManager
) {

    operator fun invoke(toUserId: String, message: String) {
//        val eventData = JSONObject()
//        eventData.put("to", toUserId)
//        eventData.put("type", "text")
//        eventData.put("content", message)
//        Log.d("Hello", "invoke: $eventData")
//        socketManager.emit("message", eventData)
    }
}