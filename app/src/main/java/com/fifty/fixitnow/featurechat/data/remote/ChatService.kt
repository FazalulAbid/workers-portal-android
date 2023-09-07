package com.fifty.fixitnow.featurechat.data.remote

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

class ChatService {

    private var socket: Socket? = null

    @Synchronized
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

    @Synchronized
    fun getSocket(): Socket? {
        return socket
    }

    @Synchronized
    fun establishConnection() {
        socket?.connect() // Check if socket is not null before connecting
    }

    @Synchronized
    fun closeConnection() {
        socket?.disconnect() // Check if socket is not null before disconnecting
        socket?.off()
    }

    @Synchronized
    fun emit(eventName: String, json: String) {
        socket?.emit(eventName, json) // Check if socket is not null before emitting
    }

    @Synchronized
    fun on(eventName: String, listener: Emitter.Listener) {
        socket?.on(eventName, listener)
    }
}