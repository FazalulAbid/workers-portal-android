package com.fifty.fixitnow.featurechat.domain.usecase

import com.fifty.fixitnow.featurechat.data.remote.SocketManager

class CloseSocketConnectionUseCase(
    private val socketManager: SocketManager
) {

    operator fun invoke() {
        socketManager.closeConnection()
    }
}