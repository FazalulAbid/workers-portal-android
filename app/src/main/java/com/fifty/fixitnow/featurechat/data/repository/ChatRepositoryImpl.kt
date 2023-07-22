package com.fifty.fixitnow.featurechat.data.repository

import com.fifty.fixitnow.featurechat.data.remote.ChatApi
import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository
import okhttp3.OkHttpClient

class ChatRepositoryImpl(
    private val chatApi: ChatApi
) : ChatRepository {


}