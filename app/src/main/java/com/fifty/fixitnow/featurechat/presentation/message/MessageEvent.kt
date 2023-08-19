package com.fifty.fixitnow.featurechat.presentation.message

sealed class MessageEvent {
    data class InputMessage(val message: String): MessageEvent()
    object SendMessage : MessageEvent()
}
