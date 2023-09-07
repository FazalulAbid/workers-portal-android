package com.fifty.fixitnow.featurechat.domain.usecase

import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository

class ObserveChatMessagesUseCase(
    private val repository: ChatRepository
) {

    operator fun invoke() = repository.observeChatMessages()
}