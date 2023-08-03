package com.fifty.fixitnow.featurechat.data.remote

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

class SocketManager {

    private var socket: Socket? = null

    fun setSocket(serverAddress: String) {
        try {
            val options = IO.Options()
            options.forceNew = true
            options.reconnection = true
            socket = IO.socket(serverAddress, options)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket? {
        return socket
    }

    fun establishConnection() {
        socket?.connect() // Check if socket is not null before connecting
    }

    fun closeConnection() {
        socket?.disconnect() // Check if socket is not null before disconnecting
    }

    fun emit(eventName: String, data: JSONObject) {
        socket?.emit(eventName, data) // Check if socket is not null before emitting
    }

    fun on(eventName: String, listener: Emitter.Listener) {
        socket?.on(
            eventName,
            listener
        ) // Check if socket is not null before registering the listener
    }
}