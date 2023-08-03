package com.fifty.fixitnow.featurechat.presentation.message

import android.util.Log
import androidx.lifecycle.ViewModel
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurechat.data.remote.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val socketManager: SocketManager
) : ViewModel() {

    init {
        socketManager.setSocket(Constants.DEBUG_WS_BASE_URL)
        socketManager.establishConnection()
    }

    fun onEvent(event: MessageEvent) {
        when (event) {
            MessageEvent.Test -> {
                socketManager.emit("chatMessage", JSONObject(mapOf("msge" to "Hello")))
            }
        }
    }
}