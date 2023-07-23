package com.fifty.fixitnow.di

import com.fifty.fixitnow.featurechat.data.remote.ChatApi
import com.fifty.fixitnow.featurechat.data.remote.SocketManager
import com.fifty.fixitnow.featurechat.data.repository.ChatRepositoryImpl
import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideChatApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): ChatApi =
        retrofit
            .client(client)
            .build()
            .create(ChatApi::class.java)

    @Provides
    @Singleton
    fun provideSocketManager(): SocketManager = SocketManager()

    @Provides
    @Singleton
    fun provideChatRepository(
        chatApi: ChatApi
    ): ChatRepository = ChatRepositoryImpl(chatApi)

}