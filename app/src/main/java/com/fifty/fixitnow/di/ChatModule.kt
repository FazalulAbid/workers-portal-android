package com.fifty.fixitnow.di

import com.fifty.fixitnow.featurechat.data.remote.ChatApi
import com.fifty.fixitnow.featurechat.data.remote.ChatService
import com.fifty.fixitnow.featurechat.data.repository.ChatRepositoryImpl
import com.fifty.fixitnow.featurechat.domain.repository.ChatRepository
import com.fifty.fixitnow.featurechat.domain.usecase.ChatSocketUseCases
import com.fifty.fixitnow.featurechat.domain.usecase.CloseSocketConnectionUseCase
import com.fifty.fixitnow.featurechat.domain.usecase.EstablishSocketConnectionUseCase
import com.fifty.fixitnow.featurechat.domain.usecase.ObserveChatMessagesUseCase
import com.fifty.fixitnow.featurechat.domain.usecase.SendMessageUseCase
import com.google.gson.Gson
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
    fun provideSocketManager(): ChatService = ChatService()

    @Provides
    @Singleton
    fun provideChatRepository(
        chatApi: ChatApi,
        chatService: ChatService,
        gson: Gson
    ): ChatRepository = ChatRepositoryImpl(chatApi, chatService, gson)

    @Provides
    @Singleton
    fun provideChatSocketUseCases(repository: ChatRepository) = ChatSocketUseCases(
        establishConnection = EstablishSocketConnectionUseCase(repository),
        sendMessage = SendMessageUseCase(repository),
        closeConnection = CloseSocketConnectionUseCase(repository),
        observeChatMessagesUseCase = ObserveChatMessagesUseCase(repository)
    )
}