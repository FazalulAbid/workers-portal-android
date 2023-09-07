package com.fifty.fixitnow.featurechat.presentation.message

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Event
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.core.util.contentUriToFileUri
import com.fifty.fixitnow.featurechat.data.remote.data.WsServerMessage
import com.fifty.fixitnow.featurechat.domain.usecase.ChatSocketUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatSocketUseCases: ChatSocketUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val chatMessages: Flow<WsServerMessage> = chatSocketUseCases.observeChatMessagesUseCase()

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _messageFieldState = mutableStateOf(StandardTextFieldState())
    val messageFieldState: State<StandardTextFieldState> = _messageFieldState

    private val _messages = MutableLiveData<String>()
    val messages: LiveData<String> = _messages

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
        chatSocketUseCases.establishConnection()
        observeChatMessages()
    }

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.SendMessage -> {
                chatSocketUseCases.sendMessage(
                    toUserId = "64f094745e8421c18b24e088",
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

    private fun observeChatMessages() {
        viewModelScope.launch {
            chatMessages.collect { message ->
                Log.d("Hello", "observeChatMessages: $message")
                _eventFlow.emit(UiEvent.MakeToast(UiText.DynamicString(message.content)))
            }
        }
    }
}