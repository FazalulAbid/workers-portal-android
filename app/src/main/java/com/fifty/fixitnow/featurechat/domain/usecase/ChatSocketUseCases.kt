package com.fifty.fixitnow.featurechat.domain.usecase

data class ChatSocketUseCases(
    val establishConnection: EstablishSocketConnectionUseCase,
    val sendMessage: SendMessageUseCase,
    val closeConnection: CloseSocketConnectionUseCase,
    val observeChatMessagesUseCase: ObserveChatMessagesUseCase
)
