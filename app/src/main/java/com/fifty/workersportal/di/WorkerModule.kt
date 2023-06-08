package com.fifty.workersportal.di

import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.repository.WorkerRepositoryImpl
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideWorkerApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): WorkerApiService =
        retrofit
            .client(client)
            .build()
            .create(WorkerApiService::class.java)

    @Provides
    @Singleton
    fun provideWorkerRepository(api: WorkerApiService): WorkerRepository =
        WorkerRepositoryImpl(api)

}