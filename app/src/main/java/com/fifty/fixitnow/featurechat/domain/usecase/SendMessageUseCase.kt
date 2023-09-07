package com.fifty.fixitnow.featurechat.domain.usecase

import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository

class SendMessageUseCase(
    private val repository: ChatRepository
) {

    operator fun invoke(toUserId: String, message: String) {
        repository.sendMessage(toUserId = toUserId, content = message)
    }
}