package com.fifty.fixitnow.featurechat.domain.usecase

import android.util.Log
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurechat.data.remote.SocketManager

class EstablishSocketConnectionUseCase(
    private val socketManager: SocketManager
) {

    operator fun invoke() {
//        Log.d("Hello", "invoke: ${Constants.DEBUG_WS_BASE_URL + "?userId=${Session.userSession.value?.id}"}")
//        socketManager.setSocket(Constants.DEBUG_WS_BASE_URL + "?userId=${Session.userSession.value?.id}")
//        socketManager.establishConnection()
    }
}