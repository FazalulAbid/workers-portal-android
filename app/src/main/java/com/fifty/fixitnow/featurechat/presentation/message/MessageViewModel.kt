package com.fifty.fixitnow.featurechat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.util.contentUriToFileUri
import com.fifty.fixitnow.featurechat.domain.usecase.ChatSocketUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatSocketUseCases: ChatSocketUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _messageFieldState = mutableStateOf(StandardTextFieldState())
    val messageFieldState: State<StandardTextFieldState> = _messageFieldState

    val predefinedMessages = listOf(
        "Hello there!",
        "Let's fix this together.",
        "You're welcome!",
        "Sorry, I am busy.",
        "Are you available tomorrow?",
        "Sure thing!",
        "No worries!",
        "I'm here to help!",
        "Thanks for reaching out!",
        "Could you please provide more details?"
    )

    init {
//
//        socketManager.establishConnection()
    }

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.SendMessage -> {
                chatSocketUseCases.sendMessage(
                    toUserId = "64bad58b8b89cc05f3c7d7ca",
                    message = _messageFieldState.value.text
                )
            }

            is MessageEvent.InputMessage -> {
                _messageFieldState.value = messageFieldState.value.copy(
                    text = event.message
                )
            }
        }
    }
}