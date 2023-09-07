package com.fifty.fixitnow.featurechat.domain.usecase

import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurechat.data.remote.ChatService
import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository

class EstablishSocketConnectionUseCase(
    private val repository: ChatRepository
) {

    operator fun invoke() {
        repository.establishChatSocketConnection(
            Constants.DEBUG_WS_BASE_URL + "?userId=${Session.userSession.value?.id}"
        )
    }
}