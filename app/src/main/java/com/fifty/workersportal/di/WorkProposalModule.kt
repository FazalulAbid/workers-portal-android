package com.fifty.workersportal.di

import com.fifty.workersportal.featureworkproposal.data.remote.WorkProposalApiService
import com.fifty.workersportal.featureworkproposal.data.repository.WorkProposalRepositoryImpl
import com.fifty.workersportal.featureworkproposal.domain.repository.WorkProposalRepository
import com.fifty.workersportal.featureworkproposal.domain.usecase.SendWorkProposalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkProposalModule {

    @Provides
    @Singleton
    fun provideWorkProposalApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): WorkProposalApiService =
        retrofit
            .client(client)
            .build()
            .create(WorkProposalApiService::class.java)

    @Provides
    @Singleton
    fun provideWorkProposalRepository(
        api: WorkProposalApiService
    ): WorkProposalRepository =
        WorkProposalRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesSendWorkProposalUseCase(
        repository: WorkProposalRepository
    ): SendWorkProposalUseCase = SendWorkProposalUseCase(repository)
}