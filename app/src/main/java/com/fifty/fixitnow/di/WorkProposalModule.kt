package com.fifty.fixitnow.di

import com.fifty.fixitnow.featurehistory.data.repository.WorkHistoryRepositoryImpl
import com.fifty.fixitnow.featurehistory.domain.repository.WorkHistoryRepository
import com.fifty.fixitnow.featurehistory.domain.usecase.GetWorkHistoryRepostUseCase
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService
import com.fifty.fixitnow.featureworkproposal.data.repository.WorkProposalRepositoryImpl
import com.fifty.fixitnow.featureworkproposal.domain.repository.WorkProposalRepository
import com.fifty.fixitnow.featureworkproposal.domain.usecase.AcceptOrRejectWorkProposalUseCase
import com.fifty.fixitnow.featureworkproposal.domain.usecase.GetWorkProposalsForWorkerUseCase
import com.fifty.fixitnow.featureworkproposal.domain.usecase.SendWorkProposalUseCase
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
    fun provideWorkHistoryRepository(
        api: WorkProposalApiService
    ): WorkHistoryRepository = WorkHistoryRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesSendWorkProposalUseCase(
        repository: WorkProposalRepository
    ): SendWorkProposalUseCase = SendWorkProposalUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWorkProposalsForWorkerUseCase(
        repository: WorkProposalRepository
    ): GetWorkProposalsForWorkerUseCase =
        GetWorkProposalsForWorkerUseCase(repository)

    @Provides
    @Singleton
    fun provideAcceptOrRejectWorkProposalUseCase(
        repository: WorkProposalRepository
    ): AcceptOrRejectWorkProposalUseCase =
        AcceptOrRejectWorkProposalUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWorkHistoryReportUseCase(
        repository: WorkHistoryRepository
    ): GetWorkHistoryRepostUseCase = GetWorkHistoryRepostUseCase(repository)
}